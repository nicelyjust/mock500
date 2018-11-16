package com.yunzhou.tdinformation.main;

import com.yunzhou.common.utils.UiUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.main
 *  @文件名:   SplashActivity
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 16:11
 *  @描述：
 */

public class SplashActivity extends BaseCommonAct {
    public boolean isFirstResume = true;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstResume) {
            UiUtils.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.start(SplashActivity.this);
                    finish();
                }
            }, 300);
        }
        isFirstResume = false;
    }
}
