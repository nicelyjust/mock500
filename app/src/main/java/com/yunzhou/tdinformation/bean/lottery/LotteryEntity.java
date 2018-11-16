package com.yunzhou.tdinformation.bean.lottery;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   LotteryEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 16:19
 *  @描述：
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.ProvinceItemAdapter;

import java.util.List;

public class LotteryEntity implements Parcelable , MultiItemEntity {
    public LotteryEntity() {
    }

    protected LotteryEntity(Parcel in) {
        id = in.readInt();
        lotteryName = in.readString();
        lotteryImg = in.readString();
        lotteryResult = in.readString();
        lotteryType = in.readString();
        abbreviation = in.readString();
    }

    public static final Creator<LotteryEntity> CREATOR = new Creator<LotteryEntity>() {
        @Override
        public LotteryEntity createFromParcel(Parcel in) {
            return new LotteryEntity(in);
        }

        @Override
        public LotteryEntity[] newArray(int size) {
            return new LotteryEntity[size];
        }
    };
    /**
     * id : 1
     * lotteryName : 大乐透
     * lotteryImg : http://39.108.61.68:80/group1/M00/00/04/rBKZOFutGJ2AXnHhAAAvabfSCIc076.png
     * lotteryResult : game_lotto_result
     * lotteryType : NATIONWIDE
     * abbreviation : lotto
     * lotteryResultDto : {"expect":"2018121","openTime":"10-15  20:35","weekDay":"星期一","openCode":"06,07,21,29,30+01,10"}
     * lotteryFunctionList : [{"id":1,"functionName":"开奖结果","functionTag":1,"lotteryId":1}]
     */

    private int id;
    private String lotteryName;
    private String lotteryImg;
    private String lotteryResult;
    private String lotteryType;
    private String abbreviation;
    private LotteryResultDtoBean lotteryResultDto;
    private List<LotteryFunctionListBean> lotteryFunctionList;

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

    public String getLotteryImg() {
        return lotteryImg;
    }

    public void setLotteryImg(String lotteryImg) {
        this.lotteryImg = lotteryImg;
    }

    public String getLotteryResult() {
        return lotteryResult;
    }

    public void setLotteryResult(String lotteryResult) {
        this.lotteryResult = lotteryResult;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public LotteryResultDtoBean getLotteryResultDto() {
        return lotteryResultDto;
    }

    public void setLotteryResultDto(LotteryResultDtoBean lotteryResultDto) {
        this.lotteryResultDto = lotteryResultDto;
    }

    public List<LotteryFunctionListBean> getLotteryFunctionList() {
        return lotteryFunctionList;
    }

    public void setLotteryFunctionList(List<LotteryFunctionListBean> lotteryFunctionList) {
        this.lotteryFunctionList = lotteryFunctionList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lotteryName);
        dest.writeString(lotteryImg);
        dest.writeString(lotteryResult);
        dest.writeString(lotteryType);
        dest.writeString(abbreviation);
    }

    @Override
    public int getItemType() {
        return ProvinceItemAdapter.TYPE_DETAIL;
    }

    public static class LotteryResultDtoBean implements Parcelable{
        /**
         * expect : 2018121
         * openTime : 10-15  20:35
         * weekDay : 星期一
         * openCode : 06,07,21,29,30+01,10
         */

        private String expect;
        private String openTime;
        private String weekDay;
        // 加号后面即是红球
        private String openCode;

        public LotteryResultDtoBean() {
        }

        protected LotteryResultDtoBean(Parcel in) {
            expect = in.readString();
            openTime = in.readString();
            weekDay = in.readString();
            openCode = in.readString();
        }

        public static final Creator<LotteryResultDtoBean> CREATOR = new Creator<LotteryResultDtoBean>() {
            @Override
            public LotteryResultDtoBean createFromParcel(Parcel in) {
                return new LotteryResultDtoBean(in);
            }

            @Override
            public LotteryResultDtoBean[] newArray(int size) {
                return new LotteryResultDtoBean[size];
            }
        };

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getOpenCode() {
            return openCode;
        }

        public void setOpenCode(String openCode) {
            this.openCode = openCode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(expect);
            dest.writeString(openTime);
            dest.writeString(weekDay);
            dest.writeString(openCode);
        }
    }

    public static class LotteryFunctionListBean {
        /**
         * id : 1
         * functionName : 开奖结果
         * functionTag : 1
         * lotteryId : 1
         */

        private int id;
        private String functionName;

        public String getChartUrl() {
            return chartUrl;
        }

        public void setChartUrl(String chartUrl) {
            this.chartUrl = chartUrl;
        }

        private String chartUrl;
        //1为开奖结果, 2为走势图表
        private int functionTag;
        private int lotteryId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFunctionName() {
            return functionName;
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        public int getFunctionTag() {
            return functionTag;
        }

        public void setFunctionTag(int functionTag) {
            this.functionTag = functionTag;
        }

        public int getLotteryId() {
            return lotteryId;
        }

        public void setLotteryId(int lotteryId) {
            this.lotteryId = lotteryId;
        }
    }
}
