package example.com.glidesample.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.glidesample.glide.CustomImageModelLoader;
import example.com.glidesample.R;
import example.com.glidesample.model.CustomImageSizeModel;
import example.com.glidesample.model.CustomImageSizeModelImp;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewPagerActivity extends AppCompatActivity {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tvLocation)
    TextView mTvLocation;

    private List<CustomImageSizeModelImp> mDatas;
    private int mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_view_pager);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        mTvLocation.setText(String.format("%d/%d", mLocation, mDatas.size()));
        mViewPager.setAdapter(new ImagePagerAdapter());
        mViewPager.setCurrentItem(mLocation);
    }


    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTvLocation.setText(String.format("%d/%d", position + 1, mDatas.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mDatas = getIntent().getParcelableArrayListExtra("datas");
        mLocation = getIntent().getIntExtra("location", 0);
    }

    class ImagePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View parent = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_image_detial, container, false);
            container.addView(parent);
            View downLoad = parent.findViewById(R.id.tvDownload);
            View loading = parent.findViewById(R.id.progressBar);
            downLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadImage(mDatas.get(position).getBaseUrl());
                }
            });
            downLoad.setTag(mDatas.get(position).getBaseUrl());
            ImageView imageView = (ImageView) parent.findViewById(R.id.imageView);
            displayImage(mDatas.get(position), imageView, loading, downLoad);
            return parent;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        void displayImage(final CustomImageSizeModel model, final ImageView imageView, final View loading, final View download) {
            DrawableRequestBuilder thumbnailBuilder = Glide
                    .with(imageView.getContext())
                    .load(new CustomImageSizeModelImp(model
                            .getBaseUrl())
                            .requestCustomSizeUrl(100, 50))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);

            Glide.with(ImageViewPagerActivity.this)
                    .using(new CustomImageModelLoader(imageView.getContext()))
                    .load(model)
//                    .load(model.getBaseUrl())
//                .centerCrop()
                    .listener(new RequestListener<CustomImageSizeModel, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, CustomImageSizeModel model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, CustomImageSizeModel model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            download.setVisibility(View.VISIBLE);
                            PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
//                            mAttacher.update();
                            return false;
                        }
                    })
                    .thumbnail(thumbnailBuilder)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }

    }

    private void downloadImage(String baseUrl) {
    }
}


