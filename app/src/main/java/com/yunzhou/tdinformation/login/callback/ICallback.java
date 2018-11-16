package com.yunzhou.tdinformation.login.callback;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.login.callback
 *  @文件名:   ICallback
 *  @创建者:   lz
 *  @创建时间:  2018/9/28 16:04
 *  @描述：
 */

import com.yunzhou.tdinformation.login.presenter.LoginPresenter;

public interface ICallback {
    LoginPresenter getPresenter();
}
