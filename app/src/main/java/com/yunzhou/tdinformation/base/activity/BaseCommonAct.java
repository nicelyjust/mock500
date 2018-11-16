package com.yunzhou.tdinformation.base.activity;
/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.activity
 *  @文件名:   BaseCommonAct
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunzhou.tdinformation.utils.ActivityStackManager;
import com.yunzhou.tdinformation.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseCommonAct extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initWindow();
        StatusBarUtil.darkMode(this,true);
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        mBind = ButterKnife.bind(this);
        initWidget(savedInstanceState);
        initData();
    }
    protected void initWindow() {
    }
    /**
     * 请求数据,子类选择实现
     */
    protected void initData() {

    }

    /**
     * 初始化组件，设置监听器，子类选择实现
     */
    protected void initWidget(Bundle savedInstanceState) {

    }

    /**
     * 布局初始化，子类必须实现
     * @return
     */
    protected abstract int getContentView();

    /*protected RequestManager getImageLoader(){
        return Glide.with(this);
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getActivityStackManager().popActivity(this);
        if (mBind != null && mBind != Unbinder.EMPTY) {
            try {
                mBind.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
