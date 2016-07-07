package com.example.imageloadpk.adapter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.imageloadpk.adapter.watcher.WatchListener;
import com.example.imageloadpk.model.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nevermore on 16/7/2.
 */
public abstract class ImageListAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<String> mDatas;
    private final WatchListener mWatchListener;

    public ImageListAdapter(Context context, WatchListener watchListener) {
        mContext = context;
        mWatchListener = watchListener;
        mDatas = new ArrayList<>();
    }

    protected void addUrl(String url) {
        mDatas.add(url);
    }

    public void setDatas() {
        Collections.addAll(mDatas, Data.URLS);
        List<String> copyDatas = new ArrayList<>(mDatas);
    }

    public void setRandomDatas() {
        Collections.addAll(mDatas, Data.URLS);
        Collections.shuffle(mDatas);
        List<String> copyDatas = new ArrayList<>(mDatas);
        mDatas.addAll(copyDatas);
        mDatas.addAll(copyDatas);
    }

    protected Context getContext() {
        return mContext;
    }

    protected WatchListener getWatchListener() {
        return mWatchListener;
    }

    public String getItem(final int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public abstract void clear();

}
