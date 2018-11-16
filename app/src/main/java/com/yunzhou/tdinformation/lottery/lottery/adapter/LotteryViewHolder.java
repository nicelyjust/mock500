package com.yunzhou.tdinformation.lottery.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   LotteryViewHolder
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 16:52
 *  @描述：
 */

public class LotteryViewHolder extends RecyclerView.ViewHolder {
    ImageView mIvLottery;
    TextView mTvWeek;
    TextView mTvTime;
    TextView mTvNo;
    TextView mTvLotteryName;
    LinearLayout mLlBallContainer;
    LinearLayout mLlFuncContainer;
    private final Context mContext;

    public LotteryViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mIvLottery = itemView.findViewById(R.id.iv_lottery);
        mTvWeek = itemView.findViewById(R.id.tv_week_lottery);
        mTvTime = itemView.findViewById(R.id.tv_time_lottery);
        mTvNo = itemView.findViewById(R.id.tv_no_lottery);
        mTvLotteryName = itemView.findViewById(R.id.tv_lottery_name);
        mLlBallContainer = itemView.findViewById(R.id.ll_ball_container_lottery);
        mLlFuncContainer = itemView.findViewById(R.id.ll_func_container_lottery);
    }

    public Context getContext() {
        return mContext;
    }
}
