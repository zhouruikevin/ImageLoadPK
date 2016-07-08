package example.com.glidesample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.glidesample.adapter.GirdAdapter;
import example.com.glidesample.R;
import example.com.glidesample.model.CustomImageSizeModel;
import example.com.glidesample.model.CustomImageSizeModelImp;
import example.com.glidesample.model.Url;

public class GirdViewActivity extends AppCompatActivity {
    public final static String TAG = "GirdViewActivity";
    public final static int STYLE_COMMON = 0;
    public final static int STYLE_WEIXIN = 1;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<CustomImageSizeModel> mDatas;
    private GirdAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gird_view);
        ButterKnife.bind(this);
        initData();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new GirdAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_NEW_YORK));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_PERU));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_TROCHILIDAE));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_1));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_2));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_3));
            mDatas.add(new CustomImageSizeModelImp(Url.IMAGE_URL_FRANCE_4));

        }
    }

    public static void actionStart(Context context, int styleFLag) {
        Intent intent = new Intent(context, GirdViewActivity.class);
        int i = (styleFLag == STYLE_COMMON) ? STYLE_COMMON : STYLE_WEIXIN;
        intent.putExtra("styleFlag", i);
        context.startActivity(intent);
    }
}
