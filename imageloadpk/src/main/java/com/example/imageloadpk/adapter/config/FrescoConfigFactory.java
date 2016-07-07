package com.example.imageloadpk.adapter.config;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by Administrator on 2016/7/4.
 */
public class FrescoConfigFactory {
    private static ImagePipelineConfig sImagePipelineConfig;

    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                    .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                            .setMaxCacheSize(ConfigConstants.MAX_CACHE_DISK_SIZE)
                            .build())
                    .setBitmapMemoryCacheParamsSupplier(
                            new Supplier<MemoryCacheParams>() {
                                @Override
                                public MemoryCacheParams get() {
                                    return new MemoryCacheParams(ConfigConstants.MAX_CACHE_MEMORY_SIZE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE);
                                }
                            }
                    )
                    .build();
        }
        return sImagePipelineConfig;
    }

    //不使用缓存
//    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
//        if (sImagePipelineConfig == null) {
//            sImagePipelineConfig = ImagePipelineConfig.newBuilder(context)
//                    .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
//                            .setMaxCacheSize(0)
//                            .build())
//                    .setBitmapMemoryCacheParamsSupplier(
//                            new Supplier<MemoryCacheParams>() {
//                                @Override
//                                public MemoryCacheParams get() {
//                                    return new MemoryCacheParams(0, 0, 0, 0, 0);
//                                }
//                            }
//                    )
//                    .build();
//        }
//        return sImagePipelineConfig;
//    }
}
