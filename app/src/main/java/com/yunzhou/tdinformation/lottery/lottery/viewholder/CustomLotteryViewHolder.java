package com.yunzhou.tdinformation.lottery.lottery.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.viewholder
 *  @文件名:   CustomLotteryViewHolder
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 11:09
 *  @描述：
 */

public class CustomLotteryViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvLotteryName;
    public CustomLotteryViewHolder(View itemView) {
        super(itemView);
        mTvLotteryName = itemView.findViewById(R.id.tv_lottery_name);
    }
}
