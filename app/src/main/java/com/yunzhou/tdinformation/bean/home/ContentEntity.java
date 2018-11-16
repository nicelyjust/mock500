package com.yunzhou.tdinformation.bean.home;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http.bean
 *  @文件名:   ContentEntity
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 15:34
 *  @描述：
 */

import java.io.Serializable;

public class ContentEntity implements Serializable{
        /**
         * id : 16
         * title : 日4
         * author : xxx
         * userId : 18
         * releaseTime : 1536893381
         * isFreeType : 1
         * tollAmount : 3
         * publisherId : null
         * source : null
         * titleImg : null
         * isRecommend : 1
         * lotteryId : null
         * lotteryExcept : null
         * intro : null
         * viewCount : 0
         * starCount : 0
         * rewardModel : null
         * status : 1
         * audit : 0
         * content : <p>是大法官</p>
         */

        private int id;
        private String title;
        private String author;
        private int userId;
        private long releaseTime;
        private int isFreeType;
        private int tollAmount;
        private String publisherId;
        private String source;
        private String titleImg;
        private int isRecommend;
        private String lotteryId;
        private String lotteryExcept;
        private String intro;
        private int viewCount;
        private int starCount;
        private String rewardModel;
        private int status;
        private int audit;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getIsFreeType() {
            return isFreeType;
        }

        public void setIsFreeType(int isFreeType) {
            this.isFreeType = isFreeType;
        }

        public int getTollAmount() {
            return tollAmount;
        }

        public void setTollAmount(int tollAmount) {
            this.tollAmount = tollAmount;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitleImg() {
            return titleImg;
        }

        public void setTitleImg(String titleImg) {
            this.titleImg = titleImg;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getLotteryId() {
            return lotteryId;
        }

        public void setLotteryId(String lotteryId) {
            this.lotteryId = lotteryId;
        }

        public String getLotteryExcept() {
            return lotteryExcept;
        }

        public void setLotteryExcept(String lotteryExcept) {
            this.lotteryExcept = lotteryExcept;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public int getStarCount() {
            return starCount;
        }

        public void setStarCount(int starCount) {
            this.starCount = starCount;
        }

        public String getRewardModel() {
            return rewardModel;
        }

        public void setRewardModel(String rewardModel) {
            this.rewardModel = rewardModel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

