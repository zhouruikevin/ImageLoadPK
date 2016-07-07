package example.com.glidesample;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CustomImageModelLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {
    public CustomImageModelLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        return model.requestCustomSizeUrl(width, height);
    }
}
