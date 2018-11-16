package com.yunzhou.tdinformation.bean;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.bean.user
 *  @文件名:   FollowEntity
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 10:47
 *  @描述：关注实体
 */

public class FollowEntity extends Entity {
    /**
     * total : 3
     * list : [{"attentionId":523,"uid":45,"followedUserUid":54,"status":1,"createTime":"2018-11-06T17:24:24.000+0800","attentionCount":1,"nickName":"小韭菜","avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAOW2ALq2XAAAqsQ5Cx7I169.jpg","intro":"健健康康快快乐乐健健康康"},{"attentionId":237,"uid":45,"followedUserUid":37,"status":1,"createTime":"2018-10-23T09:58:45.000+0800","attentionCount":0,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","intro":"业界专家，擅长通过分析亚盘、大小水位等变化竞彩。"},{"attentionId":235,"uid":45,"followedUserUid":31,"status":1,"createTime":"2018-10-23T09:55:19.000+0800","attentionCount":8,"nickName":"昊伟","avatar":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1272375279,3626809686&fm=58&bpow=272&bpoh=388","intro":"业界专家，擅长通过分析亚盘、大小水位等变化竞彩。"}]
     * pageNum : 1
     * pageSize : 10
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
    private List<FollowBean> list;
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

    public List<FollowBean> getList() {
        return list;
    }

    public void setList(List<FollowBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class FollowBean {
        /**
         * attentionId : 523
         * uid : 45
         * followedUserUid : 54
         * status : 1
         * createTime : 2018-11-06T17:24:24.000+0800
         * attentionCount : 1
         * nickName : 小韭菜
         * avatar : http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAOW2ALq2XAAAqsQ5Cx7I169.jpg
         * intro : 健健康康快快乐乐健健康康
         */

        private int attentionId;
        private int uid;
        private int followedUserUid;
        // 1:已关注 ;2:未关注
        private int status;
        private String createTime;
        private int attentionCount;
        private String nickName;
        private String avatar;
        private String intro;
        @SerializedName("myAtterntionStatus")
        private int myAttentionStatus;

        public int getMyAttentionStatus() {
            return myAttentionStatus;
        }

        public void setMyAttentionStatus(int myAttentionStatus) {
            this.myAttentionStatus = myAttentionStatus;
        }

        public int getAttentionId() {
            return attentionId;
        }

        public void setAttentionId(int attentionId) {
            this.attentionId = attentionId;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getFollowedUserUid() {
            return followedUserUid;
        }

        public void setFollowedUserUid(int followedUserUid) {
            this.followedUserUid = followedUserUid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }
}
