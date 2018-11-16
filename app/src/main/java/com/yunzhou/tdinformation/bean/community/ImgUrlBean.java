package com.yunzhou.tdinformation.bean.community;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.community
 *  @文件名:   ImgUrlBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/9 19:19
 *  @描述：
 */

public class ImgUrlBean extends Entity {
    public ImgUrlBean() {
    }

    public ImgUrlBean(String smallImg, String originalImg) {
        this.smallImg = smallImg;
        this.originalImg = originalImg;
    }

    /**
     * smallImg : http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AG7Q8AAAQZby4Qcw663.jpg
     * originalImg : http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AKZl3AAA-o0dRv6U820.jpg
     */

    private String smallImg;
    private String originalImg;
    public boolean isUpload;
    public String srcImg;
    public int pos;

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImgUrlBean) {
            ImgUrlBean another = (ImgUrlBean) obj;
            return this.srcImg.equals(another.srcImg);
        }
        return super.equals(obj);
    }
}
