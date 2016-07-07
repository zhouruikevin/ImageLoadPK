package com.example.imageloadpk.adapter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.imageloadpk.adapter.config.PicassoConfigFactory;
import com.example.imageloadpk.adapter.holders.PicassoHolder;
import com.example.imageloadpk.adapter.watcher.WatchImageView;
import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PicassoAdapter extends ImageListAdapter {
    private Picasso mPicasso;

    public PicassoAdapter(Context context, WatchListener watchListener) {
        super(context, watchListener);
        if (mPicasso == null) {
            mPicasso = PicassoConfigFactory.getPicasso(context);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WatchImageView watchImageView = new WatchImageView(getContext());
        return new PicassoHolder(watchImageView, getWatchListener(), parent, getContext(), mPicasso);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PicassoHolder picassoHolder = (PicassoHolder) holder;
        picassoHolder.bind(getItem(position));
    }

    @Override
    public void clear() {
        for (int i = 0; i < getItemCount(); i++) {
            mPicasso.invalidate(getItem(i));
        }
    }
}
