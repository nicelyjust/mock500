package com.yunzhou.tdinformation.lottery.lottery.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   CustomLocalAndHighView
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 13:44
 *  @描述：
 */

public interface CustomLocalAndHighView extends BaseView {
    void showSubscribeList(List<RecommendLotteryEntity> subscribeList);

    void showNotSubscribeList(ArrayList<MultiItemEntity> realEntity);

    void subscribeLotteryError(String message);

    void subscribeLotterySuccess(RecommendLotteryEntity item);

    void unSubscribeLotteryError(String message);

    void unSubscribeLotterySuccess(RecommendLotteryEntity item);
}
