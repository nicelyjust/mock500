package com.yunzhou.common.utils;

import android.content.Context;
import android.os.Handler;

import com.yunzhou.common.BaseApplication;


/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   UiUtils
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:04
 *  @描述：
 */
public class UiUtils {
    private UiUtils() {
    }
    public static Handler getHandler (){
        return  BaseApplication.getsHandler();
    }
    public static Context getApp (){
        return BaseApplication.getsAppContext();
    }
}
