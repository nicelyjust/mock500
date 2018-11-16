package com.yunzhou.tdinformation.bean.oder;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.oder
 *  @文件名:   RedPacksbean
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 15:58
 *  @描述：
 */

import android.os.Parcel;
import android.os.Parcelable;

public class RedPacksBean implements Parcelable {
    /**
     * id : 20
     * name : 上线大酬宾，红包不断！
     * amount : 5
     * comment : 购买购彩方案，不可兑现。
     * expireDate : 2018-10-23 09:55:30
     * needAmount : 5
     * userId : 45
     * receiveDate : 2018-10-18 09:55:30
     * isUse : 0
     */
    private int id;
    private String name;
    private int amount;
    private String comment;
    private String expireDate;
    private int needAmount;
    private int userId;
    private String receiveDate;
    private int isUse;
    public boolean isExpire;
    protected RedPacksBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readInt();
        comment = in.readString();
        expireDate = in.readString();
        needAmount = in.readInt();
        userId = in.readInt();
        receiveDate = in.readString();
        isUse = in.readInt();
    }

    public static final Creator<RedPacksBean> CREATOR = new Creator<RedPacksBean>() {
        @Override
        public RedPacksBean createFromParcel(Parcel in) {
            return new RedPacksBean(in);
        }

        @Override
        public RedPacksBean[] newArray(int size) {
            return new RedPacksBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(int needAmount) {
        this.needAmount = needAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(amount);
        dest.writeString(comment);
        dest.writeString(expireDate);
        dest.writeInt(needAmount);
        dest.writeInt(userId);
        dest.writeString(receiveDate);
        dest.writeInt(isUse);
    }
}
