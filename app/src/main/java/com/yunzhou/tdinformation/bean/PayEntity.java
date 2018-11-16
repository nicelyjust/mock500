package com.yunzhou.tdinformation.bean;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   PayEntity
 *  @创建者:   lz
 *  @创建时间:  2018/9/30 14:27
 *  @描述：    TODO
 */

public class PayEntity {

    /**
     * essayId : “文章ID”
     * money : “付款金额”
     * bettingDeadline : “距离截止时间”
     */

    private String essayId;
    private String money;
    private String bettingDeadline;

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBettingDeadline() {
        return bettingDeadline;
    }

    public void setBettingDeadline(String bettingDeadline) {
        this.bettingDeadline = bettingDeadline;
    }
}
