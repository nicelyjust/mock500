package com.yunzhou.tdinformation.mine.fans;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.FanEntity;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.follow
 *  @文件名:   MineFollowView
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 9:27
 *  @描述：
 */

public interface MineFanView extends BaseRefreshView {
    void showLoadMoreData(List<FanEntity.FanBean> list);

    void showDataView(List<FanEntity.FanBean> data);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showFollowSuccess(String msg, int pos);

    void showFollowError(String message);
}
