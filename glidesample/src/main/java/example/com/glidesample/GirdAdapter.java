package example.com.glidesample;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class GirdAdapter extends RecyclerView.Adapter {
    private List<CustomImageSizeModel> mDatas;
    private Context mContext;

    public GirdAdapter(List<CustomImageSizeModel> datas) {
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card, parent, false);
        ImageHolder imageHolder = new ImageHolder(view, parent);
        setListener(imageHolder, parent, viewType);
        return imageHolder;
    }

    private void setListener(final ImageHolder imageHolder, ViewGroup parent, int viewType) {
        imageHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = imageHolder.getPosition();
                Intent intent = new Intent(mContext, ImageViewPagerActivity.class);
                CustomImageSizeModelImp[] imps = new CustomImageSizeModelImp[mDatas.size()];
                mDatas.toArray(imps);
                intent.putParcelableArrayListExtra("datas", (ArrayList) mDatas);
                intent.putExtra("location", position);
                Log.d(TAG, "onClick: " + imps[position].getBaseUrl());
                mContext.startActivity(intent);
            }
        });
    }

    public final static String TAG = "GirdAdapter";

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ImageHolder imageHolder = (ImageHolder) holder;
        ViewGroup.LayoutParams layoutParams = imageHolder.mImageView.getLayoutParams();
        Glide.with(mContext)
                .using(new CustomImageModelLoader(mContext))
                .load(mDatas.get(position))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        private View mParentView;

        public ImageHolder(View itemView, View parentView) {
            super(itemView);
            this.mParentView = parentView;
            mImageView = (ImageView) itemView.findViewById(R.id.ivShow);
            int size = calcSize(mParentView.getWidth(), mParentView.getHeight());
            itemView.setLayoutParams(new FrameLayout.LayoutParams(size, size));
        }

        private int calcSize(int width, int height) {
            return Math.min(width, height) / 3;
        }
    }
}
