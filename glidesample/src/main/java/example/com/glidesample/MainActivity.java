package example.com.glidesample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.glidesample.ui.GirdViewActivity;
import example.com.glidesample.ui.SimpleActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.btnImagePager)
    Button mBtnImagePager;
    @Bind(R.id.btnCommonPager)
    Button mBtnCommonPager;
    @Bind(R.id.btnUseGlide)
    Button mBtnUseGlide;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btnImagePager, R.id.btnCommonPager, R.id.btnUseGlide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnImagePager:
                GirdViewActivity.actionStart(MainActivity.this, GirdViewActivity.STYLE_WEIXIN);
                break;
            case R.id.btnCommonPager:
                GirdViewActivity.actionStart(MainActivity.this, GirdViewActivity.STYLE_COMMON);
                break;
            case R.id.btnUseGlide:
                SimpleActivity.actionStart(mContext);
                break;
        }
    }
}
