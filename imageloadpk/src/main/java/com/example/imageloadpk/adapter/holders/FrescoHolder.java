package com.example.imageloadpk.adapter.holders;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.example.imageloadpk.adapter.watcher.WatchDraweeImage;
import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

/**
 * Created by Administrator on 2016/7/4.
 */
public class FrescoHolder extends BaseHolder<WatchDraweeImage> {
    public FrescoHolder(WatchDraweeImage imageView, WatchListener watchListener, View parentView, Context context) {
        super(imageView, watchListener, parentView, context);
    }

    @Override
    protected void onBind(String url) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(mImageView.getController())
                .setControllerListener(mImageView.getControllerListener())
                .setUri(Uri.parse(url))
                .build();
        mImageView.setController(draweeController);
    }
}
