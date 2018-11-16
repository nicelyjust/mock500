package com.yunzhou.tdinformation.expert.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ExpertPageView
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 19:18
 *  @描述：    TODO
 */

import com.yunzhou.tdinformation.bean.expert.ExpertIntroEntity;

public interface ExpertPageView {
    void hideLoading();

    void showExpert(ExpertIntroEntity introEntity);

    void showFollowSuccess(boolean hasFollow, String tip);

    void showFollowError(boolean hasFollow, String message);
}
