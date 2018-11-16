package com.yunzhou.tdinformation.bean.lottery;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   CountryCustomEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/25 15:26
 *  @描述：全国定制实体
 */

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;

public class CountryCustomEntity extends Entity {
    @SerializedName("notSubscribedDtoList")
    private List<RecommendLotteryEntity> notSubscribeList;
    private List<RecommendLotteryEntity> subscribeList;

    public List<RecommendLotteryEntity> getNotSubscribeList() {
        return notSubscribeList;
    }

    public void setNotSubscribeList(List<RecommendLotteryEntity> notSubscribeList) {
        this.notSubscribeList = notSubscribeList;
    }

    public List<RecommendLotteryEntity> getSubscribeList() {
        return subscribeList;
    }

    public void setSubscribeList(List<RecommendLotteryEntity> subscribeList) {
        this.subscribeList = subscribeList;
    }
}
