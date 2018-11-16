package com.yunzhou.tdinformation.community.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.view
 *  @文件名:   CircleView
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 14:09
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.community.CircleItemEntity;

import java.util.List;

public interface CircleView extends BaseView{
    void showCircleData(List<CircleItemEntity> circleItemEntities);

    void showCircleDataError(int visibility, String msg);
}
