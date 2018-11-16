package com.yunzhou.tdinformation.login.view;

import com.yunzhou.tdinformation.base.mvp.BaseView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.login.view
 *  @文件名:   ILoginView
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 15:15
 *  @描述：
 */

public interface ILoginView extends BaseView {

    int getShowType();

    void showLoginSuccess();

    void showRegisterSuccess();

    void showVerifyAuthSuccess();

    void showVerifyAuthError();

    void showToast(String msg);

    void jumpToLoginView();

    void popFragment();
}
