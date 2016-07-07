package com.example.imageloadpk.adapter.config;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Administrator on 2016/7/4.
 */

public class GlideConfigModule implements GlideModule {
    public final static String TAG = "GlideConfigModule";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, ConfigConstants.MAX_CACHE_DISK_SIZE));
        builder.setMemoryCache(new LruResourceCache(ConfigConstants.MAX_CACHE_MEMORY_SIZE));
        builder.setBitmapPool(new LruBitmapPool(ConfigConstants.MAX_CACHE_MEMORY_SIZE));
        Log.d(TAG, "applyOptions: ");
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Log.d(TAG, "registerComponents: ");
    }
}
