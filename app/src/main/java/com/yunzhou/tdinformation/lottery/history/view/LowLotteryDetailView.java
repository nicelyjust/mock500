package com.yunzhou.tdinformation.lottery.history.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.LotteryDetailEntity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.view
 *  @文件名:   LowLotteryDetailView
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 10:10
 *  @描述：
 */

public interface LowLotteryDetailView extends BaseRefreshView {
    void showEmptyView(int visibility ,boolean b);

    void showErrorView(int visibility ,boolean b);

    void showDataSuccess(LotteryDetailEntity entities);
}
