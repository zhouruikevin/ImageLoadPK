package com.example.imageloadpk.adapter.watcher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.facebook.common.internal.Preconditions;


/**
 * Created by Nevermore on 16/7/3.
 */
public class WatchImpl {
    private final Paint mPaint;
    private final Rect mRect;
    private final View mView;
    private WatchListener mWatchListener;
    private long mStartTime;
    private long mFinishTime;
    private String mTag;
    private ImageRequestState mState;

    public WatchImpl(View view) {
        mView = view;
        mPaint = new Paint();
        mRect = new Rect();
        mState = ImageRequestState.INIT;
    }

    public void init(final String tag, final WatchListener watchListener) {
        mTag = Preconditions.checkNotNull(tag);
        mWatchListener = Preconditions.checkNotNull(watchListener);
    }

    public void onStart() {
        Preconditions.checkNotNull(mTag);
        Preconditions.checkNotNull(mWatchListener);
        if (mState == ImageRequestState.STARTED) {
            onCancellation();
        }
        mState = ImageRequestState.STARTED;
        mStartTime = System.currentTimeMillis();
        mFinishTime = 0;
        mWatchListener.reportStart();

    }

    public void onSuccess() {
        Preconditions.checkState(mState == ImageRequestState.STARTED);
        mState = ImageRequestState.SUCCESS;
        mFinishTime = System.currentTimeMillis();
        final long requestTime = mFinishTime - mStartTime;
        mWatchListener.reportSuccess(requestTime);
    }

    public void onFailure() {
        Preconditions.checkState(mState == ImageRequestState.STARTED);
        mState = ImageRequestState.FAILURE;
        mFinishTime = System.currentTimeMillis();
        final long requestTime = mFinishTime - mStartTime;
        mWatchListener.reportFailure(requestTime);
    }

    public void onCancellation() {
        if (mState != ImageRequestState.STARTED) {
            return;
        }
        mState = ImageRequestState.CANCELLTION;
        mFinishTime = System.currentTimeMillis();
        final long requestTime = mFinishTime - mStartTime;
        mWatchListener.reportCancelltion(requestTime);

    }

    public void onDraw(final Canvas canvas) {
        mPaint.setColor(0xcc000000);
        mPaint.setTextSize(20f);
        mRect.set(0, 0, mView.getRight(), 40);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.WHITE);
        String msg = "init";
        switch (mState) {
            case STARTED:
                msg = "starting...";
                break;
            case SUCCESS:
                msg = "success:" + (mFinishTime - mStartTime) + "ms";
                break;
            case FAILURE:
                msg = "failure:" + (mFinishTime - mStartTime) + "ms";
                break;
            case CANCELLTION:
                msg = "cancelltion:" + (mFinishTime - mStartTime) + "ms";
                break;
        }
        canvas.drawText(msg + "\t\t" + mTag, 10, 25, mPaint);
    }

    private static enum ImageRequestState {
        INIT,
        STARTED,
        SUCCESS,
        CANCELLTION,
        FAILURE,
    }
}
