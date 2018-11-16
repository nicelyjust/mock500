package com.yunzhou.tdinformation.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.home.holder.CommonItemVH;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.adapter
 *  @文件名:   LayDetailAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 16:47
 *  @描述：
 */

public class LayDetailAdapter extends RecyclerView.Adapter {
    private static final String TAG = "LayDetailAdapter";
    private List<ContentEntity> mData;
    private Context mContext;

    public LayDetailAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    public void setData(List<ContentEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public void addAll(List<ContentEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_headline_information, parent, false);
        return new CommonItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonItemVH) {
            setCommonInfo((CommonItemVH) holder, position);
        }
    }

    private void setCommonInfo(CommonItemVH holder, int pos) {
        ContentEntity content = mData.get(pos);
        holder.mTvTitle.setText(content.getTitle());
        if (content.getIsFreeType() == 0) {
            holder.mTvSubmitTime.setCompoundDrawablePadding(0);
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            holder.mTvSubmitTime.setCompoundDrawablePadding(TDevice.dip2px( 8));
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_need_pay, 0, 0, 0);
        }
        holder.mTvSubmitTime.setText(TimeUtils.getStandardDate(1000 * content.getReleaseTime()));
        Glide.with(mContext).load(content.getTitleImg()).apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher)).into(holder.mIvImg);
        setItemClickListener(holder, pos);
    }

    private void setItemClickListener(CommonItemVH holder, int pos) {
        holder.itemView.setTag(pos);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentEntity contentEntity = mData.get((int) v.getTag());
                WebDetailActivity.start(mContext , 1, String.valueOf(contentEntity.getId()) ,String.valueOf(UserManager.getInstance().getUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
