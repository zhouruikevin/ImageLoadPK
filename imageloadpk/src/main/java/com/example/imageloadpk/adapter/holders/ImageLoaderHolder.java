package com.example.imageloadpk.adapter.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.imageloadpk.adapter.watcher.Drawables;
import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ImageLoaderHolder extends BaseHolder {

    private final ImageLoader mImageLoader;
    DisplayImageOptions mImageOptions;

    public ImageLoaderHolder(ImageView imageView, WatchListener watchListener, View parentView, Context context, ImageLoader imageLoader) {
        super(imageView, watchListener, parentView, context);
        mImageLoader = imageLoader;

        if (mImageOptions == null) {
            mImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(Drawables.sPlaceholderDrawable)
                    .showImageOnFail(Drawables.sErrorDrawable)
                    .cacheInMemory(true) //不使用内存缓存
                    .cacheOnDisk(true)  //不使用硬盘缓存
                    .build();
        }
    }

    @Override
    protected void onBind(String url) {
        mImageLoader.displayImage(url, mImageView, mImageOptions);
    }
}
