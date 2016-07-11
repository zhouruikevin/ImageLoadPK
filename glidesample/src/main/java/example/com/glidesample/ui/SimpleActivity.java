package example.com.glidesample.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.glidesample.R;
import example.com.glidesample.model.Url;

public class SimpleActivity extends AppCompatActivity {
    public final static String TAG = "SimpleActivity";
    @Bind(R.id.ivShow)
    ImageView mIvShow;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.btnLoad)
    Button btnLoad;
    @Bind(R.id.btnClear)
    Button mBtnClear;
    @Bind(R.id.btnProcessBar)
    Button mBtnProcessBar;
    @Bind(R.id.btnResize)
    Button mBtnResize;
    @Bind(R.id.btnFade)
    Button mBtnFade;
    @Bind(R.id.btnLeft)
    Button mBtnLeft;
    @Bind(R.id.btnScale)
    Button mBtnScale;
    @Bind(R.id.btnAllAnimate)
    Button mBtnAllAnimate;
    @Bind(R.id.btnPriority)
    Button mBtnPriority;
    @Bind(R.id.btnDownLoad)
    Button mBtnDownLoad;
    @Bind(R.id.btnBackground)
    Button mBtnBackground;
    @Bind(R.id.ivTonLeft)
    ImageView mIvTonLeft;
    @Bind(R.id.ivTonyRight)
    ImageView mIvTonyRight;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);
        mContext = this;
    }

    private DrawableRequestBuilder getCommGlide() {
        return Glide.with(mContext)
                .load(Url.IMAGE_URL_PERU)
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder);
    }

    private RequestListener<String, GlideBitmapDrawable> mRequestListener = new RequestListener<String, GlideBitmapDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideBitmapDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideBitmapDrawable resource, String model, Target<GlideBitmapDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            dissProgress();
            return false;
        }
    };
    private RequestListener<String, GlideBitmapDrawable> mAnimationRequestListener = new RequestListener<String, GlideBitmapDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideBitmapDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideBitmapDrawable resource, String model, Target<GlideBitmapDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            dissProgress();
            Log.d(TAG, "onResourceReady: " + isFromMemoryCache);
            if (isFromMemoryCache) {
                mIvShow.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            }
            return false;
        }
    };

    @OnClick({R.id.btnLoad, R.id.btnClear, R.id.btnProcessBar, R.id.btnResize, R.id.btnAllAnimate,
            R.id.btnFade, R.id.btnLeft, R.id.btnScale
            , R.id.btnPriority, R.id.btnDownLoad, R.id.btnBackground})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoad:
                getCommGlide()
                        .into(mIvShow);
                break;
            case R.id.btnProcessBar:
                getCommGlide()
                        .listener(mRequestListener)
                        .crossFade()
                        .into(mIvShow);
                break;
            case R.id.btnResize:
                getCommGlide()
                        .override(600, 300)
                        .listener(mRequestListener)
                        .into(mIvShow);
                break;

            case R.id.btnFade:
                getCommGlide()
                        .override(601, 301)
                        .crossFade()
                        .listener(mRequestListener)
                        .into(mIvShow);
                break;
            case R.id.btnLeft:
                getCommGlide()
                        .override(602, 302)
                        .animate(android.R.anim.slide_in_left)
                        .listener(mRequestListener)
                        .into(mIvShow);
                break;
            case R.id.btnScale:
                getCommGlide()
                        .override(603, 303)
                        .animate(R.anim.scale)
                        .listener(mRequestListener)
                        .into(mIvShow);
                break;
            case R.id.btnAllAnimate:
                getCommGlide()
                        .animate(R.anim.scale)
                        .listener(mAnimationRequestListener)
                        .into(mIvShow);
                break;
            case R.id.btnPriority:
                Glide.with(mContext)
                        .load(Url.IMAGE_URL_PERU)
                        .priority(Priority.LOW)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mIvShow);
                Glide.with(mContext)
                        .load(Url.IMAGE_URL_NEW_YORK)
                        .priority(Priority.HIGH)
                        .into(mIvTonLeft);
                Glide.with(mContext)
                        .load(Url.IMAGE_URL_TROCHILIDAE)
                        .priority(Priority.HIGH)
                        .into(mIvTonyRight);
                break;
            case R.id.btnDownLoad:
                Glide.with(mContext)
                        .load(Url.IMAGE_URL_NEW_YORK)
                        .asBitmap()
                        .priority(Priority.HIGH)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                //toSave
                                Log.d(TAG, "onResourceReady: save successful");
                            }
                        });
                break;
            case R.id.btnBackground:
                getCommGlide()
                        .into(new SimpleTarget<Drawable>(500, 100) {
                            @Override
                            public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
                                mBtnClear.setBackground(resource);
                            }
                        });
                break;
        }
    }

    private void dissProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SimpleActivity.class);
        context.startActivity(intent);
    }


}
