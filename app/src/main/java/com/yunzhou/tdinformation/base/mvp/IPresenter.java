package com.yunzhou.tdinformation.base.mvp;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.mvp
 *  @文件名:   IPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */

import java.util.List;

public interface IPresenter<T> {
    void onLoadDataEmpty(int loadType);
    void onLoadDataFailed(int loadType, String dataEmptyMsg);
    void onLoadDataSuccess(List<T> t, int loadType);
}
