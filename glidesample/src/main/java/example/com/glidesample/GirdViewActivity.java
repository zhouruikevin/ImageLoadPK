package example.com.glidesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GirdViewActivity extends AppCompatActivity {
    public final static String TAG = "GirdViewActivity";
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
        mAdapter = new GirdAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
//                mAdapter = new GirdAdapter(mDatas);
//                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void initData() {
        if (mDatas == null) {
            Log.d(TAG, "initData: ");
            mDatas = new ArrayList<>();
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/new_york.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/peru.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/trochilidae.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/france-217.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/france-220.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/france-216.jpg"));
            mDatas.add(new CustomImageSizeModelImp("http://o9xuvf3m3.bkt.clouddn.com/france-221.jpg"));
        }
    }
}
