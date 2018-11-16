package com.yunzhou.tdinformation.base.mvp;
/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.mvp
 *  @文件名:   WrapperPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */

public abstract class WrapperPresenter<T extends BaseView> implements Presenter<T>
{

    public T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public boolean isViewNotNull() {
        return mView != null;
    }
}
