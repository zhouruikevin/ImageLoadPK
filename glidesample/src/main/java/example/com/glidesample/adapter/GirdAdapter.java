package example.com.glidesample.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import example.com.glidesample.glide.CustomImageModelLoader;
import example.com.glidesample.R;
import example.com.glidesample.model.CustomImageSizeModel;
import example.com.glidesample.model.CustomImageSizeModelImp;
import example.com.glidesample.ui.GirdViewActivity;
import example.com.glidesample.ui.ImageViewPagerActivity;

/**
 * Created by Administrator on 2016/7/8.
 */
public class GirdAdapter extends RecyclerView.Adapter {
    private List<CustomImageSizeModel> mDatas;
    private Context mContext;
    private int styleFlag;

    public GirdAdapter(Context context, List<CustomImageSizeModel> datas) {
        mContext = context;
        mDatas = datas;
        styleFlag = ((Activity) context).getIntent().getIntExtra("styleFlag", GirdViewActivity.STYLE_COMMON);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
                intent.putExtra("styleFlag", styleFlag);
                intent.putParcelableArrayListExtra("datas", (ArrayList) mDatas);
                intent.putExtra("location", position);
                mContext.startActivity(intent);
            }
        });
    }

    public final static String TAG = "GirdAdapter";

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ImageHolder imageHolder = (ImageHolder) holder;
        ViewGroup.LayoutParams layoutParams = imageHolder.mImageView.getLayoutParams();
        if (styleFlag == GirdViewActivity.STYLE_COMMON) {
            Glide.with(mContext)
                    .load(mDatas.get(position).getBaseUrl())
                    .error(R.drawable.error)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            GlideDrawableImageViewTarget viewTarget = (GlideDrawableImageViewTarget) target;
//                            Drawable drawable = viewTarget.getView().getDrawable();
//                            int height = drawable.getIntrinsicHeight();
//                            int width = drawable.getIntrinsicWidth();
//                            Log.d(TAG, "onResourceReady: 界面上图片,width:" + width + ",height" + height);
//                            return false;
//                        }
//                    })
                    .into(imageHolder.mImageView);
        } else {
            Glide.with(mContext)
                    .using(new CustomImageModelLoader(mContext))
                    .load(mDatas.get(position))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.error)
                    .into(imageHolder.mImageView);
        }

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
