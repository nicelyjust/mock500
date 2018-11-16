package com.yunzhou.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.yunzhou.common.http.OkGoUtils;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common
 *  @文件名:   BaseApplication
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 10:26
 *  @描述：
 */

public class BaseApplication extends Application {
    private static Handler sHandler;
    private static Context sAppContext;

    public static Handler getsHandler() {
        return sHandler;
    }

    public static Context getsAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        sHandler = new Handler(getMainLooper());
        OkGoUtils.initOkGo(this);
    }
}
