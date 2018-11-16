package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   DiscussEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:08
 *  @描述：
 */

public class DiscussEntity extends Entity {
    /**
     * hotCommentList : []
     * generalCommentList : {"total":3,"list":[{"commentId":638,"userId":68,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"yunzhou_dugjb3","releaseTime":1540798997,"commentContent":"测试哦哦","praiseNum":0,"replyNum":0,"isPraise":0},{"commentId":637,"userId":68,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"yunzhou_dugjb3","releaseTime":1540798976,"commentContent":"我","praiseNum":0,"replyNum":0,"isPraise":0},{"commentId":636,"userId":66,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"@j\u2006k\u2006j","releaseTime":1540798936,"commentContent":"我 ","praiseNum":0,"replyNum":0,"isPraise":0}],"pageNum":1,"pageSize":5,"size":3,"startRow":1,"endRow":3,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
     */
    private GeneralCommentListBean generalCommentList;
    private List<GeneralCommentBean> hotCommentList;

    public GeneralCommentListBean getGeneralCommentList() {
        return generalCommentList;
    }

    public void setGeneralCommentList(GeneralCommentListBean generalCommentList) {
        this.generalCommentList = generalCommentList;
    }

    public List<GeneralCommentBean> getHotCommentList() {
        return hotCommentList;
    }

    public void setHotCommentList(List<GeneralCommentBean> hotCommentList) {
        this.hotCommentList = hotCommentList;
    }

    public static class GeneralCommentListBean {
        /**
         * total : 3
         * list : [{"commentId":638,"userId":68,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"yunzhou_dugjb3","releaseTime":1540798997,"commentContent":"测试哦哦","praiseNum":0,"replyNum":0,"isPraise":0},{"commentId":637,"userId":68,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"yunzhou_dugjb3","releaseTime":1540798976,"commentContent":"我","praiseNum":0,"replyNum":0,"isPraise":0},{"commentId":636,"userId":66,"avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","nickName":"@j\u2006k\u2006j","releaseTime":1540798936,"commentContent":"我 ","praiseNum":0,"replyNum":0,"isPraise":0}]
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
        private List<GeneralCommentBean> list;
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

        public List<GeneralCommentBean> getList() {
            return list;
        }

        public void setList(List<GeneralCommentBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }
    }
}
