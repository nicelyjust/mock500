package com.yunzhou.tdinformation.lottery.lottery.view;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   CustomCountryView
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 10:43
 *  @描述：
 */

public interface CustomCountryView extends BaseView {
    void showSubscribeList(List<RecommendLotteryEntity> subscribeList);

    void showNotSubscribeList(List<RecommendLotteryEntity> notSubscribeList);

    void showErrorView(int visibility);

    void subscribeLotterySuccess(RecommendLotteryEntity item);

    void subscribeLotteryError(String message);

    void unSubscribeLotterySuccess(RecommendLotteryEntity item);

    void unSubscribeLotteryError(String message);
}
