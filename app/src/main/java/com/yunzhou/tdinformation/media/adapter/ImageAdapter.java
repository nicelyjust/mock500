package com.yunzhou.tdinformation.media.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.media.bean.Image;
import com.yunzhou.tdinformation.media.config.ImageLoaderListener;


/**
 * 图片列表界面适配器
 */
public class ImageAdapter extends BaseRvAdapter<Image> {
    private ImageLoaderListener loader;
    private boolean isSingleSelect;

    public ImageAdapter(Context context, ImageLoaderListener loader) {
        super(context);
        this.loader = loader;
    }

    public void setSingleSelect(boolean singleSelect) {
        isSingleSelect = singleSelect;
    }

    @Override
    public int getItemViewType(int position) {
        Image image = getItem(position);
        if (image.getId() == 0)
            return 0;
        return 1;
    }

    public Image getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder h = (ImageViewHolder) holder;
            Glide.with(mContext).clear(h.mImageView);
        }
    }
    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new CamViewHolder(mInflater.inflate(R.layout.item_list_cam, parent, false));
        return new ImageViewHolder(mInflater.inflate(R.layout.item_list_image, parent, false));
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, Image image, int position) {
        if (image.getId() != 0) {
            ImageViewHolder h = (ImageViewHolder) holder;
            h.mCheckView.setSelected(image.isSelect());
            h.mMaskView.setVisibility(image.isSelect() ? View.VISIBLE : View.GONE);

            loader.displayImage(h.mImageView, image.getPath());
            h.mCheckView.setVisibility(isSingleSelect ? View.GONE : View.VISIBLE);
        }
    }

    private static class CamViewHolder extends RecyclerView.ViewHolder {
        CamViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        ImageView mCheckView;
        View mMaskView;

        ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            mCheckView = (ImageView) itemView.findViewById(R.id.cb_selected);
            mMaskView = itemView.findViewById(R.id.lay_mask);
        }
    }
}
