package com.example.imageloadpk.adapter.watcher;

/**
 * Created by Nevermore on 16/7/3.
 */
public class WatchListener {
    private long mRequestTotalTime;
    private long mStartRequests;
    private long mSuccessedRequests;
    private long mFailedRequests;
    private long mCancelledRequests;

    public void reportSuccess(long requestTime) {
        mRequestTotalTime += requestTime;
        mSuccessedRequests++;
    }

    public void initData() {
        mRequestTotalTime = 0;
        mStartRequests = 0;
        mSuccessedRequests = 0;
        mFailedRequests = 0;
        mCancelledRequests = 0;
    }

    public void reportStart() {
        mStartRequests++;
    }

    public void reportCancelltion(long requestTime) {
        mCancelledRequests++;
        mRequestTotalTime += requestTime;
    }

    public void reportFailure(long requestTime) {
        mFailedRequests++;
        mRequestTotalTime += requestTime;
    }


    public long getAverageRequestTime() {
        final long completedRequests = getCompletedRequests();
        return completedRequests > 0 ? mRequestTotalTime / completedRequests : 0;
    }

    public long getCancelledRequests() {
        return mCancelledRequests;
    }

    public long getCompletedRequests() {
        return mSuccessedRequests + mCancelledRequests + mFailedRequests;
    }

    public long getTotalRequests() {
        return mStartRequests;
    }
}
