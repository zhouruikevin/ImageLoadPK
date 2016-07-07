package com.example.imagescaletype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ivTop)
    ImageView mIvTop;
    @Bind(R.id.ivBottom)
    ImageView mIvBottom;
    @Bind(R.id.center)
    RadioButton mCenter;
    @Bind(R.id.centerCrop)
    RadioButton mCenterCrop;
    @Bind(R.id.centerInside)
    RadioButton mCenterInside;
    @Bind(R.id.fitCenter)
    RadioButton mFitCenter;
    @Bind(R.id.fitStart)
    RadioButton mFitStart;
    @Bind(R.id.fitEnd)
    RadioButton mFitEnd;
    @Bind(R.id.fitXY)
    RadioButton mFitXY;
    @Bind(R.id.matrix)
    RadioButton mMatrix;
    List<RadioButton> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list.add(mCenter);
        list.add(mCenterCrop);
        list.add(mCenterInside);
        list.add(mFitCenter);
        list.add(mFitStart);
        list.add(mFitEnd);
        list.add(mFitXY);
        list.add(mMatrix);

    }

    @OnClick({R.id.center, R.id.centerCrop, R.id.centerInside, R.id.fitCenter, R.id.fitStart, R.id.fitEnd, R.id.fitXY, R.id.matrix})
    public void onClick(View view) {
        if (view instanceof RadioButton) {
            isSelected((RadioButton) view);
        }
        switch (view.getId()) {
            case R.id.center:
                setScaleType(ImageView.ScaleType.CENTER);
                break;
            case R.id.centerCrop:
                setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case R.id.centerInside:
                setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case R.id.fitCenter:
                setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case R.id.fitStart:
                setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case R.id.fitEnd:
                setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case R.id.fitXY:
                setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case R.id.matrix:
                setScaleType(ImageView.ScaleType.MATRIX);
                break;
        }
    }


    private void isSelected(RadioButton btn) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != btn) {
                list.get(i).setChecked(false);
            }
        }
    }

    private void setScaleType(ImageView.ScaleType scaleType) {
        mIvTop.setScaleType(scaleType);
        mIvBottom.setScaleType(scaleType);
    }
}
