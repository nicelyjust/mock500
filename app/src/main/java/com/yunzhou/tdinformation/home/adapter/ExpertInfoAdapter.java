package com.yunzhou.tdinformation.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;
import com.yunzhou.tdinformation.expert.ExpertPageViewActivity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.adapter
 *  @文件名:   ExpertInfoAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 9:55
 *  @描述：
 */

public class ExpertInfoAdapter extends RecyclerView.Adapter<ExpertInfoAdapter.ExpertVH> {

    private List<ExpertEntity> mData;
    private Context mContext;

    public ExpertInfoAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    public void setData(List<ExpertEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    @NonNull
    @Override
    public ExpertVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_headline_spec_info, parent, false);
        return new ExpertVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertVH holder, int position) {
        ExpertEntity expertEntity = mData.get(position);
        holder.mTvInformationDesc.setText(expertEntity.getNickName());
        holder.mTvWinRate.setVisibility(View.GONE);
        Glide.with(mContext).load(expertEntity.getAvatar())
                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.default_head))
                .into(holder.mIvSpecHead);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpertEntity entity = mData.get((Integer) v.getTag());
                ExpertPageViewActivity.start(mContext , entity.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ExpertVH extends RecyclerView.ViewHolder {
        ImageView mIvSpecHead;
        TextView mTvInformationDesc;
        TextView mTvWinRate;

        public ExpertVH(View itemView) {
            super(itemView);
            mIvSpecHead = itemView.findViewById(R.id.iv_spec_head);
            mTvInformationDesc = itemView.findViewById(R.id.tv_information_desc);
            mTvWinRate = itemView.findViewById(R.id.tv_win_rate);
        }
    }
}
