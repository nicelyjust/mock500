package com.yunzhou.tdinformation.bean.lottery;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   LotteryProvinceEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 15:37
 *  @描述：
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LotteryProvinceEntity implements Parcelable {
    /**
     * id : 5
     * province : 内蒙古
     * lotteryList : [{"id":4,"lotteryName":"排列3","lotteryImg":"http://39.108.61.68:80/group1/M00/00/04/rBKZOFutGHaAbFmHAAAsgB-7NL0519.png","lotteryResult":"game_pl3_result","lotteryType":"NATIONWIDE","abbreviation":"pl3","lotteryResultDto":{"expect":"2018282","openTime":"10-16  20:43","weekDay":"星期二","openCode":"7,8,0"},"hasOnline":false,"lotteryFunctionList":[{"id":5,"functionName":"开奖结果","functionTag":1,"lotteryId":4}]}]
     */
    private int id;
    private String province;
    private List<LotteryEntity> lotteryList;

    public LotteryProvinceEntity() {
    }

    protected LotteryProvinceEntity(Parcel in) {
        id = in.readInt();
        province = in.readString();
        lotteryList = in.createTypedArrayList(LotteryEntity.CREATOR);
    }

    public static final Creator<LotteryProvinceEntity> CREATOR = new Creator<LotteryProvinceEntity>() {
        @Override
        public LotteryProvinceEntity createFromParcel(Parcel in) {
            return new LotteryProvinceEntity(in);
        }

        @Override
        public LotteryProvinceEntity[] newArray(int size) {
            return new LotteryProvinceEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<LotteryEntity> getLotteryList() {
        return lotteryList;
    }

    public void setLotteryList(List<LotteryEntity> lotteryList) {
        this.lotteryList = lotteryList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(province);
        dest.writeTypedList(lotteryList);
    }
}
