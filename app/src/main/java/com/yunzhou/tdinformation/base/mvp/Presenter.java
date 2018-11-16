package com.yunzhou.tdinformation.base.mvp;
/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.mvp
 *  @文件名:   Presenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */

public interface Presenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
    // 释放资源的操作
    void destroy();
}
