package com.yunzhou.tdinformation.lottery.lottery.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   LotteryHistoryView
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 11:25
 *  @描述：
 */

public interface LotteryHistoryView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showLoadMoreData(List<LotteryEntity.LotteryResultDtoBean> lotteryResults);

    void showDataView(List<LotteryEntity.LotteryResultDtoBean> data);
}
