package com.yunzhou.tdinformation.home.holder;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.holder
 *  @文件名:   HeaderViewHolder
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 17:38
 *  @描述：
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.youth.banner.Banner;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.view.widget.MarqueeView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "HeaderViewHolder";
    public Banner mBanner;
    public MarqueeView mMarqueeView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        mBanner = itemView.findViewById(R.id.banner_head_line);
        mMarqueeView = itemView.findViewById(R.id.mv_ad);
    }
}
