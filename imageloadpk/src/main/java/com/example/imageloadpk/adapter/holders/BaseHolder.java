package com.example.imageloadpk.adapter.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.imageloadpk.adapter.watcher.WatchInterface;
import com.example.imageloadpk.adapter.watcher.WatchListener;

/**
 * Created by Nevermore on 16/7/3.
 */
public abstract class BaseHolder<V extends ImageView & WatchInterface> extends RecyclerView.ViewHolder {
    protected final V mImageView;
    private final WatchListener mWatchListener;
    private final View mParentView;
    private Context mContext;

    public BaseHolder(V imageView, WatchListener watchListener, View parentView, Context context) {
        super(imageView);
        mImageView = imageView;
        mWatchListener = watchListener;
        mParentView = parentView;
        mContext = context;
        if (mParentView != null) {
            int size = calcDesiredSize(mParentView.getWidth(), mParentView.getHeight());
            updateViewLayoutParams(mImageView, size, size);
        }
    }

    private void updateViewLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null || layoutParams.height != width || layoutParams.width != height) {
            layoutParams = new AbsListView.LayoutParams(width, height);
            view.setLayoutParams(layoutParams);
        }
    }


    protected int calcDesiredSize(int width, int height) {
        return width / 2;
    }

    public void bind(String url) {
        mImageView.initWatcher(url, mWatchListener);
        onBind(url);
    }

    protected abstract void onBind(String url);

    protected Context getContext() {
        return mContext;
    }


}
