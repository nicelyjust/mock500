package com.yunzhou.tdinformation.bean.oder;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.oder
 *  @文件名:   PayTypeBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 15:57
 *  @描述：
 */

import java.io.Serializable;

public class PayTypeBean implements Serializable{
    /**
     * payCode : 1003
     * payName : 彩币
     */

    private String payCode;
    private String payName;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}
