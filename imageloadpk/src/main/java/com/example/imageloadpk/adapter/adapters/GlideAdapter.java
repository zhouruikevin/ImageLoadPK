package com.example.imageloadpk.adapter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.imageloadpk.adapter.holders.GlideHolder;
import com.example.imageloadpk.adapter.watcher.WatchImageView;
import com.example.imageloadpk.adapter.watcher.WatchListener;


/**
 * Created by Nevermore on 16/7/1.
 */
public class GlideAdapter extends ImageListAdapter {
    public GlideAdapter(Context context, WatchListener watchListener) {
        super(context, watchListener);
    }

    @Override
    public GlideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final WatchImageView watchImageView = new WatchImageView(getContext());
        return new GlideHolder(watchImageView, getWatchListener(), parent, getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GlideHolder glideHolder = (GlideHolder) holder;
        glideHolder.bind(getItem(position));
    }

    @Override
    public void clear() {
        Glide.get(getContext()).clearMemory();
    }
}
