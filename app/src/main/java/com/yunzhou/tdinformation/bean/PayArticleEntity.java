package com.yunzhou.tdinformation.bean;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   PayArticleEntity
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 10:41
 *  @描述：
 */

public class PayArticleEntity extends Entity {

    /**
     * total : 2
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * articles : [{"orderId":56,"orderNum":"20181023105319693713","contentId":25,"viewCount":70,"releaseTime":1540260233,"lotteryName":"大乐透","uid":37,"avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","nickName":"宁财神","canBuy":1,"canBet":1,"opened":0,"title":"七哥侃球：上期中4+0，这期跑不了，超级大乐透18125期重点分析"},{"orderId":54,"orderNum":"20181022125207086169","contentId":24,"viewCount":230,"releaseTime":1540171934,"lotteryName":"大乐透","uid":38,"avatar":"http://39.108.61.68:80/group1/M00/00/0C/rBKZOFvbwD2AGb4tAAknaS_XZ3U967.jpg@@http://39.108.61.68:80/group1/M00/00/0C/rBKZOFvbwD2AEspPAAAhL2wW65w079.jpg","nickName":"福彩专家","canBuy":0,"canBet":0,"opened":1,"title":"蛋大2018124期大乐透分析，上期战绩又增加，小奖剪不断今晚等你"}]
     */

    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private List<ArticlesBean> articles;

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

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean extends Entity{
        /**
         * orderId : 56
         * orderNum : 20181023105319693713
         * contentId : 25
         * viewCount : 70
         * releaseTime : 1540260233
         * lotteryName : 大乐透
         * uid : 37
         * avatar : http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg
         * nickName : 宁财神
         * canBuy : 1
         * canBet : 1
         * opened : 0
         * title : 七哥侃球：上期中4+0，这期跑不了，超级大乐透18125期重点分析
         */

        private int orderId;
        private String orderNum;
        private int contentId;
        private int viewCount;
        private long releaseTime;
        private String lotteryName;
        private int uid;
        private String avatar;
        private String nickName;
        private int canBuy;
        private int canBet;
        private int opened;
        private String title;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getContentId() {
            return contentId;
        }

        public void setContentId(int contentId) {
            this.contentId = contentId;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(int releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getLotteryName() {
            return lotteryName;
        }

        public void setLotteryName(String lotteryName) {
            this.lotteryName = lotteryName;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getCanBuy() {
            return canBuy;
        }

        public void setCanBuy(int canBuy) {
            this.canBuy = canBuy;
        }

        public int getCanBet() {
            return canBet;
        }

        public void setCanBet(int canBet) {
            this.canBet = canBet;
        }

        public int getOpened() {
            return opened;
        }

        public void setOpened(int opened) {
            this.opened = opened;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
