package com.example.imageloadpk.adapter.config;

import android.content.Context;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PicassoConfigFactory {
    private static Picasso sPicasso;

    public static Picasso getPicasso(Context context) {
        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(context)
                    //硬盘缓存池大小
                    .downloader(new OkHttpDownloader(context, ConfigConstants.MAX_CACHE_DISK_SIZE))
                    //内存缓存池大小
                    .memoryCache(new LruCache(ConfigConstants.MAX_CACHE_MEMORY_SIZE))
//                    .defaultBitmapConfig(Bitmap.Config.ARGB_4444)
                    .build();
        }
        return sPicasso;
    }
}
