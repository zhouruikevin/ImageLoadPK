package example.com.glidesample;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CustomImageSizeModelImp implements CustomImageSizeModel, Parcelable {
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

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.baseUrl);
    }

    protected CustomImageSizeModelImp(Parcel in) {
        this.baseUrl = in.readString();
    }

    public static final Parcelable.Creator<CustomImageSizeModelImp> CREATOR = new Parcelable.Creator<CustomImageSizeModelImp>() {
        @Override
        public CustomImageSizeModelImp createFromParcel(Parcel source) {
            return new CustomImageSizeModelImp(source);
        }

        @Override
        public CustomImageSizeModelImp[] newArray(int size) {
            return new CustomImageSizeModelImp[size];
        }
    };
}
