package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   CustomLotteryEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/25 15:22
 *  @描述：高频以及地方彩返回数据
 */

public class CustomLotteryEntity extends Entity {

    private List<NotSubscribedBean> notSubscribedDtoList;
    private List<RecommendLotteryEntity> subscribeList;

    public List<NotSubscribedBean> getNotSubscribedDtoList() {
        return notSubscribedDtoList;
    }

    public void setNotSubscribedDtoList(List<NotSubscribedBean> notSubscribedDtoList) {
        this.notSubscribedDtoList = notSubscribedDtoList;
    }

    public List<RecommendLotteryEntity> getSubscribeList() {
        return subscribeList;
    }

    public void setSubscribeList(List<RecommendLotteryEntity> subscribeList) {
        this.subscribeList = subscribeList;
    }
}
