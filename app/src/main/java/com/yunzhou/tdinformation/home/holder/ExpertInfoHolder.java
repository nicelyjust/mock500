package com.yunzhou.tdinformation.home.holder;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.holder
 *  @文件名:   ExpertInfoHolder
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 15:07
 *  @描述：
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;

public class ExpertInfoHolder extends RecyclerView.ViewHolder {
    public TextView mTvMoreExpert;
    public RecyclerView mRvExpert;
    public ExpertInfoHolder(View itemView) {
        super(itemView);
        mTvMoreExpert = itemView.findViewById(R.id.tv_more_expert);
        mRvExpert = itemView.findViewById(R.id.rv_expert);
    }
}
