package com.yunzhou.tdinformation.bean.lottery;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.common.http.bean.base.Entity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.ProvinceItemAdapter;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   RecommendLotteryEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 19:30
 *  @描述：
 */

public class RecommendLotteryEntity extends Entity implements MultiItemEntity {
    /**
     * id : 1
     * lotteryName : 大乐透
     * provinceId : -1
     */

    private int id;
    private String lotteryName;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecommendLotteryEntity) {
            if (this == obj) {
                return true;
            }
            return ((RecommendLotteryEntity) obj).getId() == this.getId();
        } else {
            return false;
        }
    }

    @Override
    public int getItemType() {
        return ProvinceItemAdapter.TYPE_DETAIL;
    }
}
