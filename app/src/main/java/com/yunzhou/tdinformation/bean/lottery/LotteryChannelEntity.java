package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   LotteryChannelEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 10:54
 *  @描述：
 */

public class LotteryChannelEntity extends Entity {

    /**
     * channelId : 6
     * channelName : 大乐透
     * isShow : 1
     * orderNum : 1
     * parentId : 1
     */

    private int channelId;
    private String channelName;
    private int isShow;
    private int orderNum;
    private int parentId;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
