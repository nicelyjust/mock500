package com.yunzhou.tdinformation.bean.home;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.home
 *  @文件名:   BannerEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 16:20
 *  @描述：
 */

public class BannerEntity extends Entity {

    private List<CarouselBean> carouselsList;
    private List<AnnouncementBean> announcementList;

    public List<CarouselBean> getCarouselsList() {
        return carouselsList;
    }

    public void setCarouselsList(List<CarouselBean> carouselsList) {
        this.carouselsList = carouselsList;
    }

    public List<AnnouncementBean> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<AnnouncementBean> announcementList) {
        this.announcementList = announcementList;
    }

    public static class CarouselBean extends Entity {
        /**
         * id : 1
         * originalImg : http://39.108.61.68:80/group1/M00/00/06/rBKZOFuu9hmAJUy7AAIWvJTB5eI401.png
         * smallImg : http://39.108.61.68:80/group1/M00/00/06/rBKZOFuu9hiAVRphAAA7OmQaGoo255.png
         * createTime : 2018-09-28 14:44:54
         * updateTime : 2018-09-28 14:44:57
         * status : 1
         * carouselUrl : http://192.168.0.137:8080/examples/activity/InviteFriends.html
         * carouselOrder : 1
         * descript : 邀好友赚红包
         */

        private int id;
        private String originalImg;
        private String smallImg;
        private String createTime;
        private String updateTime;
        private int status;
        private String carouselUrl;
        private int carouselOrder;
        private String descript;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginalImg() {
            return originalImg;
        }

        public void setOriginalImg(String originalImg) {
            this.originalImg = originalImg;
        }

        public String getSmallImg() {
            return smallImg;
        }

        public void setSmallImg(String smallImg) {
            this.smallImg = smallImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCarouselUrl() {
            return carouselUrl;
        }

        public void setCarouselUrl(String carouselUrl) {
            this.carouselUrl = carouselUrl;
        }

        public int getCarouselOrder() {
            return carouselOrder;
        }

        public void setCarouselOrder(int carouselOrder) {
            this.carouselOrder = carouselOrder;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }
    }

    public static class AnnouncementBean extends Entity {
        /**
         * id : 3
         * name : 大乐透爆1注千万头奖 奖池65.57亿再创新高
         * msgBody : 9月26日晚，体彩大乐透第18113期开奖，全国仅浙江中出1注1000万元头奖。当期开奖过后，大乐透奖池上升至65.57亿元
         * status : 1
         * msgUrl : http://www.baidu.com
         * msgCategory : 0
         * createTime : 2018-09-28 16:00:07
         * updateTime : 2018-09-28 16:00:10
         */

        private int id;
        private String name;
        private String msgBody;
        private int status;
        private String msgUrl;
        private int msgCategory;
        private String createTime;
        private String updateTime;

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

        public String getMsgBody() {
            return msgBody;
        }

        public void setMsgBody(String msgBody) {
            this.msgBody = msgBody;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsgUrl() {
            return msgUrl;
        }

        public void setMsgUrl(String msgUrl) {
            this.msgUrl = msgUrl;
        }

        public int getMsgCategory() {
            return msgCategory;
        }

        public void setMsgCategory(int msgCategory) {
            this.msgCategory = msgCategory;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
