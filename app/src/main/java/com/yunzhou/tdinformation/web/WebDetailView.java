package com.yunzhou.tdinformation.web;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.web
 *  @文件名:   WebDetailView
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 15:17
 *  @描述：
 */

import android.support.annotation.StringRes;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.oder.OderEntity;

public interface WebDetailView extends BaseView{
    void showToast(String msg);
    void showToast(@StringRes int msg);

    void showOderDialog(OderEntity content);

    void showBtnLoading();

    void hideBtnLoading();

    void showGetJwtOk(String payJWT);

    void showPaySuccess();
}
