package com.yunzhou.tdinformation.home.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.holder
 *  @文件名:   CommonItemVH
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 15:11
 *  @描述：
 */

public class CommonItemVH extends RecyclerView.ViewHolder {
    public ImageView mIvImg;
    public TextView mTvTitle;
    public TextView mTvSubmitTime;
    public CommonItemVH(View itemView) {
        super(itemView);
        mIvImg = itemView.findViewById(R.id.iv_headline_img);
        mTvTitle = itemView.findViewById(R.id.tv_headline_title);
        mTvSubmitTime = itemView.findViewById(R.id.tv_submit_time);
    }
}
