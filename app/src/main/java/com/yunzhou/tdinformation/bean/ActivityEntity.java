package com.yunzhou.tdinformation.bean;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   ActivityEntity
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 12:45
 *  @描述：
 */

public class ActivityEntity extends Entity {

    /**
     * total : 3
     * list : [{"id":3,"name":"金赢在线","originalImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbyUqAMx5fAAA-Gk_hnVI64.jpeg","smallImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbyUqAZTTEAAAmaBhbomw34.jpeg","carouselUrl":"http://39.108.61.68:8080/examples/activity/signUp.html","descript":"金赢在线","activityStart":"2017-09-03 00:00:00","activityEnd":"2017-09-28 00:00:00"},{"id":2,"name":"每周竞猜","originalImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvcM36AFALGAAAnE8D0Bvg49.jpeg","smallImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvcM36AAmRAAAAi5pG73zY35.jpeg","carouselUrl":"http://39.108.61.68:8080/examples/activity/activity.html","descript":"每周竞猜描述","activityStart":"2018-10-02 00:00:00","activityEnd":"2018-11-30 00:00:00"},{"id":1,"name":"邀好友赚红包","originalImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbxsuABehrAAAUBemouRQ44.jpeg","smallImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbxsuASfiTAAAR8erSh8c66.jpeg","carouselUrl":"http://39.108.61.68:8080/examples/activity/InviteFriends.html","descript":"邀好友赚红包","activityStart":"2018-11-01 00:00:00","activityEnd":"2018-11-02 00:00:00"}]
     * pageNum : 1
     * pageSize : 5
     * size : 3
     * startRow : 1
     * endRow : 3
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * firstPage : 1
     * lastPage : 1
     */

    private int total;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;
    private List<ActivityBean> list;
    private List<Integer> navigatepageNums;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
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

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<ActivityBean> getList() {
        return list;
    }

    public void setList(List<ActivityBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ActivityBean {
        /**
         * id : 3
         * name : 金赢在线
         * originalImg : http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbyUqAMx5fAAA-Gk_hnVI64.jpeg
         * smallImg : http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvbyUqAZTTEAAAmaBhbomw34.jpeg
         * carouselUrl : http://39.108.61.68:8080/examples/activity/signUp.html?uid=45&activityId=1
         * descript : 金赢在线
         * activityStart : 2017-09-03 00:00:00
         * activityEnd : 2017-09-28 00:00:00
         */

        private int id;
        private String name;
        private String originalImg;
        private String smallImg;
        private String carouselUrl;
        @SerializedName("descript")
        private String desc;
        private String activityStart;
        private String activityEnd;

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

        public String getCarouselUrl() {
            return carouselUrl;
        }

        public void setCarouselUrl(String carouselUrl) {
            this.carouselUrl = carouselUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getActivityStart() {
            return activityStart;
        }

        public void setActivityStart(String activityStart) {
            this.activityStart = activityStart;
        }

        public String getActivityEnd() {
            return activityEnd;
        }

        public void setActivityEnd(String activityEnd) {
            this.activityEnd = activityEnd;
        }
    }
}
