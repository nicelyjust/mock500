package com.yunzhou.tdinformation.bean.lottery;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   LotteryHistoryEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 13:56
 *  @描述：    http://39.108.61.68:8082/app/lottery/result/findResultListByDate?pageNo=1&pageSize=10&lotteryId=27
 */

public class LotteryHistoryEntity extends Entity {
    /**
     * total : 53
     * list : [{"expect":"2018082","openTime":"10-19  20:37","weekDay":"星期五","openCode":"07,10,20,21,24,29,31+08"},{"expect":"2018081","openTime":"10-16  20:36","weekDay":"星期二","openCode":"01,07,13,18,23,29,33+02"},{"expect":"2018080","openTime":"10-12  21:40","weekDay":"星期五","openCode":"02,04,07,11,30,32,34+15"},{"expect":"2018079","openTime":"10-09  20:43","weekDay":"星期二","openCode":"07,09,12,17,22,28,34+21"},{"expect":"2018078","openTime":"10-05  20:38","weekDay":"星期五","openCode":"05,09,13,19,21,23,32+20"},{"expect":"2018077","openTime":"10-02  20:40","weekDay":"星期二","openCode":"01,02,10,17,21,23,25+24"},{"expect":"2018076","openTime":"09-28  20:45","weekDay":"星期五","openCode":"04,06,19,21,22,26,32+23"},{"expect":"2018075","openTime":"09-25  20:39","weekDay":"星期二","openCode":"02,04,12,13,20,29,34+09"},{"expect":"2018074","openTime":"09-21  20:38","weekDay":"星期五","openCode":"04,10,18,23,27,29,34+25"},{"expect":"2018073","openTime":"09-18  21:15","weekDay":"星期二","openCode":"04,07,13,18,29,33,35+15"}]
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * pages : 6
     * prePage : 0
     * nextPage : 2
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4,5,6]
     * navigateFirstPage : 1
     * navigateLastPage : 6
     * firstPage : 1
     * lastPage : 6
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

    public List<LotteryEntity.LotteryResultDtoBean> getLotteryResults() {
        return lotteryResults;
    }

    public void setLotteryResults(List<LotteryEntity.LotteryResultDtoBean> lotteryResults) {
        this.lotteryResults = lotteryResults;
    }

    @SerializedName("list")

    private List<LotteryEntity.LotteryResultDtoBean> lotteryResults;
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

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
}
