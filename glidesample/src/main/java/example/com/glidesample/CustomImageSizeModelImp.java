package example.com.glidesample;

import android.util.Log;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CustomImageSizeModelImp implements CustomImageSizeModel {
    private String baseUrl;
    private static final String TAG = "CustomImageSizeModelImp";

    public CustomImageSizeModelImp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {
        String url = baseUrl + "?imageView2/2/h/" + height + "/w/" + width;
        Log.d(TAG, "requestCustomSizeUrl: " + url);
        return url;

    }
}
