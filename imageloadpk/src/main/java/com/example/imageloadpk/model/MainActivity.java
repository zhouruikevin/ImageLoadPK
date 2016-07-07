package com.example.imageloadpk.model;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.imageloadpk.R;
import com.example.imageloadpk.adapter.adapters.FrescoAdapter;
import com.example.imageloadpk.adapter.adapters.GlideAdapter;
import com.example.imageloadpk.adapter.adapters.ImageListAdapter;
import com.example.imageloadpk.adapter.adapters.ImageLoaderAdapter;
import com.example.imageloadpk.adapter.adapters.PicassoAdapter;
import com.example.imageloadpk.adapter.watcher.Drawables;
import com.example.imageloadpk.adapter.watcher.WatchListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.tvShowInfo)
    TextView mTvShowInfo;
    @Bind(R.id.rbGlide)
    RadioButton mRbGlide;
    @Bind(R.id.rbPicasso)
    RadioButton mRbPicasso;
    @Bind(R.id.rbImageLoad)
    RadioButton mRbImageLoad;
    @Bind(R.id.rbFresco)
    RadioButton mRbFresco;
    private GlideAdapter mAdapter;
    private WatchListener mWatchListener;
    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            upTvShowInfo();
            scheduleNextShow();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new Handler(getMainLooper());
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        scheduleNextShow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        upTvShowInfo();
        cancelScheduleRunnable();
    }

    private void initView() {
        Drawables.init(getResources());
        mWatchListener = new WatchListener();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearOtherLoader();
                mRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void scheduleNextShow() {
        mHandler.postDelayed(mRunnable, 1000);
    }

    private void cancelScheduleRunnable() {
        mHandler.removeCallbacks(mRunnable);
    }

    public final static long MB = 1024 * 1024;

    private void upTvShowInfo() {
        final Runtime runtime = Runtime.getRuntime();
        final float totalMemory = (runtime.totalMemory()) / MB;
        final float heapMemory = (runtime.totalMemory() - runtime.freeMemory()) / MB;
        String heapStr = String.format("已使用内存:%.1f M\t总共:%.1f M\n", heapMemory, totalMemory);
        String formatStr = "平均请求时间:%dms\t 总共加载次数:%d\t已完成次数:%d\t取消次数:%d";
        String result = String.format(formatStr, mWatchListener.getAverageRequestTime(),
                mWatchListener.getTotalRequests(), mWatchListener.getCompletedRequests(),
                mWatchListener.getCancelledRequests());

        mTvShowInfo.setText(heapStr + result);
    }

    @OnClick({R.id.rbGlide, R.id.rbPicasso, R.id.rbImageLoad, R.id.rbFresco})
    public void onClick(View view) {
        clearOtherLoader();
        switch (view.getId()) {
            case R.id.rbGlide:
                loadData(new GlideAdapter(this, mWatchListener));
                break;
            case R.id.rbPicasso:
                loadData(new PicassoAdapter(this, mWatchListener));
                break;
            case R.id.rbImageLoad:
                loadData(new ImageLoaderAdapter(this, mWatchListener));
                break;
            case R.id.rbFresco:
                loadData(new FrescoAdapter(this, mWatchListener));
                break;
        }
    }

    private void clearOtherLoader() {
        if (mAdapter != null) {
            mAdapter.clear();
        }
        if (mWatchListener != null) {
            mWatchListener.initData();
        }
    }

    private void loadData(ImageListAdapter adapter) {
        adapter.setRandomDatas();
        mRecyclerView.setAdapter(adapter);
    }
}
