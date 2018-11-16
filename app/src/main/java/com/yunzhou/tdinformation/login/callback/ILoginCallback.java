package com.yunzhou.tdinformation.login.callback;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.onLogin.callback
 *  @文件名:   ILoginCallback
 *  @创建者:   lz
 *  @创建时间:  2018/9/28 14:21
 *  @描述：
 */

public interface ILoginCallback extends ICallback{
    void onForgetPassword();

    void onRegister();
}
