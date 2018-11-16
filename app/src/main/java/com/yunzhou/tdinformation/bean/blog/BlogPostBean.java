package com.yunzhou.tdinformation.bean.blog;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.blog
 *  @文件名:   BlogPostBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/22 14:51
 *  @描述：
 */

public class BlogPostBean extends Entity {
    private String content;
    private int uid;
    private int circleType;
    private List<Pictures> imgList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCircleType() {
        return circleType;
    }

    public void setCircleType(int circleType) {
        this.circleType = circleType;
    }

    public List<Pictures> getImgList() {
        return imgList;
    }

    public void setImgList(List<Pictures> imgList) {
        this.imgList = imgList;
    }
    public static class Pictures extends Entity{
        public Pictures() {
        }

        public Pictures(String smallImg, String originalImg) {
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
    }
    @Override
    public String toString() {
        return "BlogPostBean{" +
                "content='" + content + '\'' +
                ", uid=" + uid +
                ", circleType=" + circleType +
                ", imgList=" + imgList +
                '}';
    }
}
