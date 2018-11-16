package com.yunzhou.tdinformation.bean.oder;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.oder
 *  @文件名:   OderEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 15:56
 *  @描述：
 */

public class OderEntity extends Entity {

    /**
     * payType : {"payCode":"1003","payName":"彩币"}
     * orderMoney : 60.00
     * discount : {"discountName":"无可用","discountId":1}
     * orderNum : 20181018155507117586
     * redPacks : [{"id":20,"name":"上线大酬宾，红包不断！","amount":5,"comment":"购买购彩方案，不可兑现。","expireDate":"2018-10-23 09:55:30","needAmount":5,"userId":45,"receiveDate":"2018-10-18 09:55:30","isUse":0},{"id":21,"name":"上线大酬宾，红包不断！","amount":5,"comment":"购买购彩方案，不可兑现。","expireDate":"2018-10-23 09:55:33","needAmount":5,"userId":45,"receiveDate":"2018-10-18 09:55:33","isUse":0},{"id":22,"name":"上线大酬宾，红包不断！","amount":5,"comment":"购买购彩方案，不可兑现。","expireDate":"2018-10-23 09:55:35","needAmount":5,"userId":45,"receiveDate":"2018-10-18 09:55:35","isUse":0}]
     */

    private PayTypeBean payType;
    private String orderMoney;
    // 用以显示是否有可用红包
    private DiscountBean discount;
    private String orderNum;
    private List<RedPacksBean> redPacks;

    public PayTypeBean getPayType() {
        return payType;
    }

    public void setPayType(PayTypeBean payType) {
        this.payType = payType;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public DiscountBean getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountBean discount) {
        this.discount = discount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<RedPacksBean> getRedPacks() {
        return redPacks;
    }

    public void setRedPacks(List<RedPacksBean> redPacks) {
        this.redPacks = redPacks;
    }


    public static class DiscountBean {
        /**
         * discountName : 无可用
         * discountId : 1
         */

        private String discountName;
        private int discountId;

        public String getDiscountName() {
            return discountName;
        }

        public void setDiscountName(String discountName) {
            this.discountName = discountName;
        }

        public int getDiscountId() {
            return discountId;
        }

        public void setDiscountId(int discountId) {
            this.discountId = discountId;
        }
    }

}
