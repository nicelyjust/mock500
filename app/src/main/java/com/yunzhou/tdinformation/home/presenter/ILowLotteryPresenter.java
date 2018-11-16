package com.yunzhou.tdinformation.home.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.presenter
 *  @文件名:   ILowLotteryPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 18:41
 *  @描述：
 */


import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;

import java.util.List;

public interface ILowLotteryPresenter {
    void onDataInfoSuccess(int loadType, List<ContentEntity> contents);

    void onDataInfoError(int loadType, String msg);

    void loadChannelSuccess(List<LotteryChannelEntity> entities);

    void loadChannelLotterySuccess(int pos, LotteryEntity.LotteryResultDtoBean entity);
}
