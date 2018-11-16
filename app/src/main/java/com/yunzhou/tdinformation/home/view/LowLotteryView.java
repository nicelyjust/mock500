package com.yunzhou.tdinformation.home.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.view
 *  @文件名:   LowLotteryView
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 16:43
 *  @描述：
 */

public interface LowLotteryView extends BaseRefreshView {
    void showLoadMoreHeadLineData(List<ContentEntity> contents);

    void showHeadLineDataView(List<ContentEntity> contents);

    void showLotteryColumn(List<LotteryChannelEntity> entities);

    void loadLotteryView(int pos, LotteryEntity.LotteryResultDtoBean entity);
}
