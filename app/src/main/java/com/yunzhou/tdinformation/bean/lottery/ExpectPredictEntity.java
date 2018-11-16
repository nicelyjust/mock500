package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   ExpectPredictEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:28
 *  @描述：
 */

public class ExpectPredictEntity extends Entity {

    /**
     * contentId : 50
     * contentTitle : 熊浩熊浩呼叫熊浩，熊浩熊浩呼叫熊浩
     * isRecommend : 0
     */

    private int contentId;
    private String contentTitle;
    private int isRecommend;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }
}
