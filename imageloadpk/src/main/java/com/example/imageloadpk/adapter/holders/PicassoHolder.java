package com.example.imageloadpk.adapter.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.imageloadpk.adapter.watcher.Drawables;
import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PicassoHolder extends BaseHolder {
    private final Picasso mPicasso;

    public PicassoHolder(ImageView imageView, WatchListener watchListener, View parentView, Context context, Picasso picasso) {
        super(imageView, watchListener, parentView, context);
        mPicasso = picasso;
    }

    @Override
    protected void onBind(String url) {
        mPicasso.load(url)
                .placeholder(Drawables.sPlaceholderDrawable)
                .error(Drawables.sErrorDrawable)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//不使用内存缓存
//                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)//不使用磁盘缓存
//                .fit()

                .into(mImageView);
    }
}
