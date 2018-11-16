package com.yunzhou.tdinformation.pay.callback;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay.callback
 *  @文件名:   IRedFragmentListener
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 10:10
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.oder.RedPacksBean;

public interface IRedFragmentListener {
    void onSelRedPacketBack(RedPacksBean redPacksBean);
}
