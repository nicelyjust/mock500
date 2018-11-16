package com.yunzhou.tdinformation.lottery.lottery.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   LocalView
 *  @创建者:   lz
 *  @创建时间:  2018/10/23 12:23
 *  @描述：
 */

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.LotteryProvinceEntity;

import java.util.ArrayList;
import java.util.List;

public interface LocalView extends BaseRefreshView{
    void showLocalLottery(List<LotteryProvinceEntity> provinceEntity);

    void showEmptyView(int visible);

    void showErrorView(int visible);

    void showLoadMoreLocalLottery(ArrayList<MultiItemEntity> provinceEntity);

    void showLocalLottery(ArrayList<MultiItemEntity> realEntity);
}
