package com.example.imageloadpk.adapter.holders;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.imageloadpk.adapter.watcher.Drawables;
import com.example.imageloadpk.adapter.watcher.WatchImageView;
import com.example.imageloadpk.adapter.watcher.WatchListener;

/**
 * Created by Nevermore on 16/7/3.
 */
public class GlideHolder extends BaseHolder<WatchImageView> {

    public GlideHolder(WatchImageView imageView, WatchListener watchListener, View parentView, Context context) {
        super(imageView, watchListener, parentView, context);
    }

    public final static String TAG = "GlideHolder";
    private RequestListener<String, GlideDrawable> mRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Log.w(TAG, "onException: ", e);
            Log.d(TAG, "onException: " + model);
            Log.d(TAG, "onException: " + target.getRequest().isRunning());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    @Override
    protected void onBind(String url) {
        Glide.with(getContext())
                .load(url)
                .listener(mRequestListener)
                .placeholder(Drawables.sPlaceholderDrawable)
                .error(Drawables.sErrorDrawable)
                .centerCrop()
//                .skipMemoryCache(true) //不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
                .into(mImageView);
    }
}
