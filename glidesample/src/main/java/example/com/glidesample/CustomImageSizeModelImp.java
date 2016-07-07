package example.com.glidesample;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CustomImageSizeModelImp implements CustomImageSizeModel {
    private String baseUrl;

    public CustomImageSizeModelImp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {

        return baseUrl + "?imageView2/2/h/" + height + "/w/" + width;
    }
}
