package com.yunzhou.tdinformation.bean.community;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.community
 *  @文件名:   CircleItemEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 14:41
 *  @描述：
 */

public class CircleItemEntity extends Entity {

    /**
     * circleId : 1
     * circleType : 1
     * circleTitle : 数字达人
     * circleIntroduce : 数字彩的聚集地
     * newNum : 0
     */

    private int circleId;
    private int circleType;
    private String circleTitle;
    private String circleIntroduce;
    private int newNum;

    public String getCircleUrl() {
        return circleUrl;
    }

    public void setCircleUrl(String circleUrl) {
        this.circleUrl = circleUrl;
    }

    private String circleUrl;

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getCircleType() {
        return circleType;
    }

    public void setCircleType(int circleType) {
        this.circleType = circleType;
    }

    public String getCircleTitle() {
        return circleTitle;
    }

    public void setCircleTitle(String circleTitle) {
        this.circleTitle = circleTitle;
    }

    public String getCircleIntroduce() {
        return circleIntroduce;
    }

    public void setCircleIntroduce(String circleIntroduce) {
        this.circleIntroduce = circleIntroduce;
    }

    public int getNewNum() {
        return newNum;
    }

    public void setNewNum(int newNum) {
        this.newNum = newNum;
    }
}
