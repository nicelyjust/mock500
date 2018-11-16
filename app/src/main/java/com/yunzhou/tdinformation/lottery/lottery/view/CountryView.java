package com.yunzhou.tdinformation.lottery.lottery.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   CountryView
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 16:07
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;

import java.util.List;

public interface CountryView extends BaseRefreshView{
    void showLotteryData(List<LotteryEntity> lotteryEntities);

    void showErrorView(int visibility);
}
