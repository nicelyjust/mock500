package com.yunzhou.tdinformation.home.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.presenter
 *  @文件名:   IHeadLinePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 9:15
 *  @描述：
 */


import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;

import java.util.List;

public interface IHeadLinePresenter {

    void onHeadLineError(int loadType, String exception);

    void onHeadLineSuccess(int loadType, List<ContentEntity> contents);

    void onBannerSuccess(BannerEntity bannerEntity);

    void onBannerError(String msg);

    void onLoadExpertSuccess(List<ExpertEntity> expertEntities);

    void onLoadExpertError(String message);
}
