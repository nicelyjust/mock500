package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   NotSubscribedBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 11:32
 *  @描述：
 */

public  class NotSubscribedBean extends Entity{
    /**
     * provinceId : 3
     * province : 河北
     * lotteryList : [{"id":19,"lotteryName":"河北福彩20选5","provinceId":3}]
     */

    private int provinceId;
    private String province;
    private List<RecommendLotteryEntity> lotteryList;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<RecommendLotteryEntity> getLotteryList() {
        return lotteryList;
    }

    public void setLotteryList(List<RecommendLotteryEntity> lotteryList) {
        this.lotteryList = lotteryList;
    }
}
