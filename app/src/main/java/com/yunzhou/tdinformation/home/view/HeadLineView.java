package com.yunzhou.tdinformation.home.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   HeadLineView
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 18:56
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;

import java.util.List;

public interface HeadLineView extends BaseRefreshView {
    void showBannerView(BannerEntity bannerEntity);

    void showLoadMoreHeadLineData(List<ContentEntity> contents);

    void showHeadLineDataView(List<ContentEntity> contents);

    void showExpertView(List<ExpertEntity> expertEntities);
}
