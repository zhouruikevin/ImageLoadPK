package com.example.imageloadpk.adapter.watcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.common.internal.Preconditions;

/**
 * Created by Nevermore on 16/7/3.
 */
public class WatchImageView extends ImageView implements WatchInterface {
    private final WatchImpl mWatcher;

    public WatchImageView(Context context) {
        super(context);
        //设置图片填充样式为按比例填满控件
        setScaleType(ScaleType.CENTER_CROP);
        mWatcher = new WatchImpl(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制结果会用到，通过mWatcher转发
        mWatcher.onDraw(canvas);
    }

//    禁用这个方法，防止框架使用它显示图片，影响我们测试显示
    @Override
    public void setImageURI(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initWatcher(String tag, WatchListener watchListener) {
        mWatcher.init(tag, watchListener);
        mWatcher.onStart(); //初始化
    }

    @Override
    public void setImageDrawable(Drawable drawable) {

        Preconditions.checkNotNull(drawable);
        if (drawable == Drawables.sPlaceholderDrawable) {
            //不再这里调用，在初始化加载器，Glide.build()实现调用更好
        } else if (drawable == Drawables.sErrorDrawable) {
            mWatcher.onFailure();//加载失败
        } else {
            mWatcher.onSuccess();//加载成功
        }
        super.setImageDrawable(drawable);
    }
}
