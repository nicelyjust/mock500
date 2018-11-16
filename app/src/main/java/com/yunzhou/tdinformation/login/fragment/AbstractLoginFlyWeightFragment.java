package com.yunzhou.tdinformation.login.fragment;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.login.fragment
 *  @文件名:   AbstractLoginFlyWeightFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/28 11:50
 *  @描述：
 */

import android.support.annotation.StringRes;

import com.yunzhou.tdinformation.base.fragment.BaseFragment;

public abstract class AbstractLoginFlyWeightFragment extends BaseFragment{
    @StringRes
    public abstract int getTitle();

}
