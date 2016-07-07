package com.example.imageloadpk.adapter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.imageloadpk.adapter.config.ImageLoaderFactory;
import com.example.imageloadpk.adapter.holders.ImageLoaderHolder;
import com.example.imageloadpk.adapter.watcher.WatchImageView;
import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ImageLoaderAdapter extends ImageListAdapter {

    final private ImageLoader mImageLoader;

    public ImageLoaderAdapter(Context context, WatchListener watchListener) {
        super(context, watchListener);
        mImageLoader = ImageLoaderFactory.getImageLoader(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WatchImageView watchImageView = new WatchImageView(getContext());
        return new ImageLoaderHolder(watchImageView, getWatchListener(), parent, getContext(), mImageLoader);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageLoaderHolder imageLoaderHolder = (ImageLoaderHolder) holder;
        imageLoaderHolder.bind(getItem(position));
    }

    @Override
    public void clear() {
        mImageLoader.clearMemoryCache();
    }

}
