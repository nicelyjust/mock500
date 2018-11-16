package com.yunzhou.tdinformation.base.mvp;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.base
 *  @文件名:   BaseRefreshView
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 10:42
 *  @描述：
 */

public interface BaseRefreshView extends BaseView {
    void finishLoadMore(int delay, boolean success, boolean noMoreData);

    void finishRefresh(boolean success);
}
