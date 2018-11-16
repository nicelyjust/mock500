package com.yunzhou.tdinformation.pay.callback;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay.callback
 *  @文件名:   IOderFragmentListener
 *  @创建者:   lz
 *  @创建时间:  2018/9/30 18:06
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.oder.RedPacksBean;

import java.util.List;

public interface IOderFragmentListener {
    void onPayBtnClick();

    List<RedPacksBean> onSelectRed();
}
