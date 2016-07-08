package example.com.glidesample.glide;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import example.com.glidesample.model.CustomImageSizeModel;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CustomImageSizeModelFactory implements ModelLoaderFactory<CustomImageSizeModel, InputStream> {
    public final static String TAG  =  "CustomImageSizeModelFactory";
    @Override
    public ModelLoader<CustomImageSizeModel, InputStream> build(Context context, GenericLoaderFactory factories) {
        return new CustomImageModelLoader(context);
    }

    @Override
    public void teardown() {
    }
}
