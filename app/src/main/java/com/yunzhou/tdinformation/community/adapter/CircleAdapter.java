package com.yunzhou.tdinformation.community.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.adapter
 *  @文件名:   CircleAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 14:13
 *  @描述：    TODO
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.community.CircleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.CircleVH> {

    private final List<CircleItemEntity> mData;

    private Context mContext;
    private Callback mCallback;

    public CircleAdapter(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public CircleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_circle, parent, false);
        return new CircleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircleVH holder, int position) {
        CircleItemEntity circleItemEntity = mData.get(position);
        String circleTitle = circleItemEntity.getCircleTitle();
        Glide.with(mContext).load(circleItemEntity.getCircleUrl()).into(holder.mIvImg);
        holder.mSplitLine.setVisibility(position == mData.size() - 1 ? View.GONE : View.VISIBLE);
        holder.mTvTag.setText(circleTitle);
        holder.mTvIntro.setText(circleItemEntity.getCircleIntroduce());
        holder.mTvNewPostNum.setText(String.valueOf(circleItemEntity.getNewNum()));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClick((int) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<CircleItemEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public CircleItemEntity getItem(int pos) {
        return this.mData.get(pos);
    }

    static class CircleVH extends RecyclerView.ViewHolder {
        ImageView mIvImg;
        TextView mTvTag;
        TextView mTvIntro;
        TextView mTvNewPostNum;
        View mSplitLine;

        public CircleVH(View itemView) {
            super(itemView);
            mIvImg = itemView.findViewById(R.id.iv_img_circle);
            mTvTag = itemView.findViewById(R.id.tv_tag_circle);
            mTvIntro = itemView.findViewById(R.id.tv_intro_circle);
            mTvNewPostNum = itemView.findViewById(R.id.tv_new_post_num);
            mSplitLine = itemView.findViewById(R.id.split_line);
        }
    }

    public interface Callback {
        void onItemClick(int pos);
    }
}
