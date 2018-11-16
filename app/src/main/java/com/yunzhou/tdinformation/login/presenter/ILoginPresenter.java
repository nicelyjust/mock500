package com.yunzhou.tdinformation.login.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.onLogin.presenter
 *  @文件名:   ILoginPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/28 14:58
 *  @描述：
 */

import android.support.annotation.StringRes;

import com.yunzhou.tdinformation.bean.user.UserEntity;


public interface ILoginPresenter {

    void onSendAuthSuccess(int type, String auth);

    void onSendAuthError(int type, @StringRes int error);

    void onSendAuthError(int type, String error);

    void onLoginSuccess(String jwt, UserEntity userEntity);

    void onRegisterSuccess();

    void onRegisterError(String msg);

    void onLoginError(String msg);

    void onVerifyAuthSuccess();

    void onVerifyAuthError();

    void settingNewPsdSuccess();

    void settingNewPsdError(String msg);
}
