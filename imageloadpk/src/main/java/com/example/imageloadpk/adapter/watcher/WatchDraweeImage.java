package com.example.imageloadpk.adapter.watcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/7/4.
 */
public class WatchDraweeImage extends SimpleDraweeView implements WatchInterface {
    private final WatchImpl mWatcher;
    private ControllerListener mControllerListener;

    public WatchDraweeImage(Context context, GenericDraweeHierarchy genericDraweeHierarchy) {
        super(context, genericDraweeHierarchy);
        setScaleType(ScaleType.CENTER_CROP);
        mWatcher = new WatchImpl(this);
    }

    @Override
    public void initWatcher(String tag, WatchListener watchListener) {
        mWatcher.init(tag, watchListener);
        mControllerListener = new BaseControllerListener() {
            @Override
            public void onRelease(String id) {
                super.onRelease(id);
                mWatcher.onCancellation();
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                mWatcher.onFailure();
            }

            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
                mWatcher.onStart();
            }

            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                mWatcher.onSuccess();
            }
        };
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        SimpleDraweeControllerBuilder controllerBuilder = getControllerBuilder().setUri(uri);
        if (controllerBuilder instanceof AbstractDraweeControllerBuilder) {
            ((AbstractDraweeControllerBuilder) controllerBuilder).setControllerListener(mControllerListener);
        }
        setController(controllerBuilder.build());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWatcher.onDraw(canvas);
    }

    public ControllerListener getControllerListener() {
        return mControllerListener;
    }
}
