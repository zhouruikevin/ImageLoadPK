package example.com.glidesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ivShow)
    ImageView mIvShow;
    @Bind(R.id.btLoading)
    Button mBtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    CustomImageSizeModel mCustomImageSizeModel = new CustomImageSizeModelImp(Url.IMAGE_URL_1);

    private void initView() {
        mBtLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCustomImageSizeModel.requestCustomSizeUrl(20, 40);
                Glide.with(MainActivity.this)
                        .load(mCustomImageSizeModel)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(mIvShow);
            }
        });
    }
}
