package com.yunzhou.tdinformation.mine.red.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   IMyRedPacketModel
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 10:35
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.oder.RedPacksBean;

import java.util.List;

public interface IMyRedPacketModel {
    void loadRedSuccess(int loadType, List<RedPacksBean> data);

    void loadRedError(int loadType, String message);
}
