package com.yunzhou.tdinformation.bean.expert;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.expert
 *  @文件名:   ArticleEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:17
 *  @描述：
 */

public class ArticleEntity extends Entity {

    /**
     * total : 22
     * pages : 11
     * prePage : 0
     * contents : [{"id":22,"title":"商业屠夫 双色球 2018121期：双色球看盘","author":"昊伟","userId":31,"releaseTime":1539668854,"isFreeType":1,"tollAmount":18,"source":"本站编辑","titleImg":"http://39.108.61.68:80/group1/M00/00/09/rBKZOFvFe3GAQGOcAABN03OQqLs99.jpeg","isRecommend":0,"lotteryId":8,"viewCount":0,"starCount":0,"status":0,"audit":1},{"id":21,"title":"姐妹花联手 男子爱倍投 两地彩友各中排列5百万奖","author":"宁财神","userId":37,"releaseTime":1539262979,"isFreeType":1,"tollAmount":10,"titleImg":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFu_SeiAQW0IAABHQQ-8qic74.jpeg","isRecommend":0,"lotteryId":13,"viewCount":40,"starCount":0,"status":0,"audit":1}]
     * nextPage : 2
     */

    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private List<ArticleBean> contents;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<ArticleBean> getContents() {
        return contents;
    }

    public void setContents(List<ArticleBean> contents) {
        this.contents = contents;
    }

    public static class ArticleBean extends Entity{
        /**
         * id : 22
         * title : 商业屠夫 双色球 2018121期：双色球看盘
         * author : 昊伟
         * userId : 31
         * releaseTime : 1539668854
         * isFreeType : 1
         * tollAmount : 18.0
         * source : 本站编辑
         * titleImg : http://39.108.61.68:80/group1/M00/00/09/rBKZOFvFe3GAQGOcAABN03OQqLs99.jpeg
         * isRecommend : 0
         * lotteryId : 8
         * viewCount : 0
         * starCount : 0
         * status : 0
         * audit : 1
         */

        private int id;
        private String title;
        private String author;
        private int userId;
        private long releaseTime;
        private int isFreeType;
        private double tollAmount;
        private String source;
        private String titleImg;
        private int isRecommend;
        private int lotteryId;
        private int viewCount;
        private int starCount;
        private int status;
        private int audit;

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

        public void setReleaseTime(int releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getIsFreeType() {
            return isFreeType;
        }

        public void setIsFreeType(int isFreeType) {
            this.isFreeType = isFreeType;
        }

        public double getTollAmount() {
            return tollAmount;
        }

        public void setTollAmount(double tollAmount) {
            this.tollAmount = tollAmount;
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

        public int getLotteryId() {
            return lotteryId;
        }

        public void setLotteryId(int lotteryId) {
            this.lotteryId = lotteryId;
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
    }
}
