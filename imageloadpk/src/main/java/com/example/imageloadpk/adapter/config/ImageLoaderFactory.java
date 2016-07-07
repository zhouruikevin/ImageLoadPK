package com.example.imageloadpk.adapter.config;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ImageLoaderFactory {
    private static ImageLoader sImageLoader;


    public static ImageLoader getImageLoader(Context context) {


        if (sImageLoader == null) {
            ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                    .diskCacheSize(ConfigConstants.MAX_CACHE_DISK_SIZE)
                    .memoryCacheSize(ConfigConstants.MAX_CACHE_MEMORY_SIZE)
                    .build();

            sImageLoader = ImageLoader.getInstance();
            sImageLoader.init(imageLoaderConfiguration);
        }
        return sImageLoader;
    }
}
