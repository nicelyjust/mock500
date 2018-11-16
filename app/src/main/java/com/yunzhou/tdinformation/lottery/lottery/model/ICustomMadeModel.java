package com.yunzhou.tdinformation.lottery.lottery.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.model
 *  @文件名:   ICustomMadeModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 18:57
 *  @描述：    TODO
 */

import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;

import java.util.List;

public interface ICustomMadeModel {
    void loadCustomLotterySuccess(int loadType, List<SubscribeLotteryEntity> entities);

    void loadCustomLotteryError(int loadType, String message);

    void loadRecommendLotterySuccess(int loadType, List<RecommendLotteryEntity> entities);

    void loadRecommendLotteryError(int loadType, String message);

    void subscribeLotterySuccess();

    void subscribeLotteryError(String message);
}
