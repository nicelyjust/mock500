package com.yunzhou.tdinformation.lottery.lottery.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   CustomMadeView
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 17:42
 *  @描述：
 */

public interface CustomMadeView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showCustomLotteryEmpty(boolean needChange);

    void showRecommendLotteryEmpty(boolean needChange);

    void showRecommendLottery(List<RecommendLotteryEntity> entities);

    void showCustomLottery(List<SubscribeLotteryEntity> entities);

    void showSubscribeLotteryError(String message);

    void showSubscribeLotterySuccess();
}
