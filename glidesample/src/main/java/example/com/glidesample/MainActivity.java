package example.com.glidesample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.glidesample.model.CustomImageSizeModelImp;
import example.com.glidesample.model.Url;
import example.com.glidesample.ui.GirdViewActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.ivShow)
    ImageView mIvShow;
    @Bind(R.id.btLoading)
    Button mBtLoading;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.btnClear)
    Button mBtnClear;
    @Bind(R.id.btnImagePager)
    Button mBtnImagePager;
    @Bind(R.id.btnCommonPager)
    Button mBtnCommonPager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mContext = this;
    }

    CustomImageSizeModelImp mCustomImageSizeModel = new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1);
    RequestListener<String, GlideDrawable> mRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            mProgressBar.setVisibility(View.INVISIBLE);
            return false;
        }
    };


    private void initView() {
        mBtLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressBar.setVisibility(View.VISIBLE);

                DrawableRequestBuilder drawableRequestBuilder = Glide
                        .with(mContext)
                        .load(mCustomImageSizeModel.requestCustomSizeUrl(100, 50))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);

                Glide.with(mContext)
//                        .using(new CustomImageModelLoader(MainActivity.this))
//                        .load(new CustomImageSizeModelImp(Url.IMAGE_URL_1))
                        .load(Url.IMAGE_URL_FRANCE_1)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(mRequestListener)
                        .skipMemoryCache(true)
                        .thumbnail(drawableRequestBuilder)
                        .into(mIvShow);
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Glide.clear(mIvShow);
            }
        });
    }

    @OnClick({R.id.btnImagePager, R.id.btnCommonPager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnImagePager:
                GirdViewActivity.actionStart(MainActivity.this, GirdViewActivity.STYLE_WEIXIN);
                break;
            case R.id.btnCommonPager:
                GirdViewActivity.actionStart(MainActivity.this, GirdViewActivity.STYLE_COMMON);
                break;
        }
    }
}
