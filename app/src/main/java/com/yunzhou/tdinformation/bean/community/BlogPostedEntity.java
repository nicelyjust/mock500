package com.yunzhou.tdinformation.bean.community;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.community
 *  @文件名:   BlogPostedEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 18:20
 *  @描述：
 */

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;

public class BlogPostedEntity extends Entity{
    /**
     * total : 10
     * list : [{"postId":48,"content":"121212","createTime":"2018-10-08 11:37:55","author":"yunzhou_u16or5","status":4,"circleType":1,"uid":47,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AG7Q8AAAQZby4Qcw663.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AKZl3AAA-o0dRv6U820.jpg"}],"authorNickName":"Yyyyyyyyyyy","authorAvatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwIISAbrKgABS5bt3vsfQ607.jpg","isLike":0,"isCollect":0,"collectNum":3,"commitNum":14,"likeNum":2},{"postId":50,"content":"Ugly girls who want gib hknn go big od night amp evening or night or Saturday 🤢🤮🤮😷😷🤒e","createTime":"2018-09-30 19:59:24","author":"yunzhou_dt4h1f","status":1,"circleType":3,"uid":48,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvaaCAJos-AAAn0FJ-ks4651.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvaaGAKcx6AAvDjqfIZsI953.jpg"}],"authorNickName":"Asdfasdfasdfasd","authorAvatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvabqAFmwRAAZwOfmUt1A246.jpg","isLike":0,"isCollect":0,"collectNum":3,"commitNum":20,"likeNum":3},{"postId":51,"content":"Asdasdasdasdasd","createTime":"2018-09-29 20:05:58","author":"yunzhou_echiph","status":1,"circleType":4,"uid":49,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvaqaASa7wAAAgpofqVPI841.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvaqaAIPcpAA_VXmlLS34324.jpg"}],"authorNickName":"yunzhou_echiph","authorAvatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvapaAMV1vAA8wPyHqyQM814.jpg","isLike":0,"isCollect":0,"collectNum":1,"commitNum":1,"likeNum":0},{"postId":54,"content":"我来发个帖子","createTime":"2018-09-30 09:09:38","author":"宁财神","status":1,"circleType":2,"uid":37,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwIlGAH-8kAAAl88R5E_U701.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwIlKALSvPAC3wGebXsMc849.jpg"}],"authorNickName":"宁财神","authorAvatar":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2499316035,2541377038&fm=58&bpow=291&bpoh=342","isLike":0,"isCollect":0,"collectNum":9,"commitNum":13,"likeNum":3},{"postId":55,"content":"哈哈","createTime":"2018-09-30 09:56:55","author":"宁财神","status":1,"circleType":2,"uid":37,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwLWaAbpcWAAAl88R5E_U335.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwLWaAbgnTAC3wGebXsMc427.jpg"}],"authorNickName":"宁财神","authorAvatar":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2499316035,2541377038&fm=58&bpow=291&bpoh=342","isLike":0,"isCollect":0,"collectNum":1,"commitNum":3,"likeNum":2},{"postId":56,"content":"我来做测试","createTime":"2018-09-30 19:57:45","author":"yunzhou_3638vr","status":1,"circleType":1,"uid":51,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwi2yAfHlNAAAbMQL38hM570.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwi2yAd-97ABbkH615RJI493.jpg"}],"authorNickName":"yunzhou_3638vr","isLike":0,"isCollect":0,"collectNum":1,"commitNum":0,"likeNum":1},{"postId":58,"content":"客厅扣咯哦模样","createTime":"2018-09-30 19:59:13","author":"yunzhou_3638vr","status":1,"circleType":1,"uid":51,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwl7uAMtcXAAAbMQL38hM145.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwl7uAEM9_ABbkH615RJI065.jpg"}],"authorNickName":"yunzhou_3638vr","isLike":0,"isCollect":0,"collectNum":1,"commitNum":0,"likeNum":0},{"postId":59,"content":"什么鬼软件无比学科你大喊大叫你的不对","createTime":"2018-09-30 19:49:05","author":"宁财神","status":1,"circleType":5,"uid":37,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwuDCAT8FxAAAMjb2CvjY559.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFuwuDCAMFL_AAMh_-dL8wQ350.jpg"}],"authorNickName":"宁财神","authorAvatar":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2499316035,2541377038&fm=58&bpow=291&bpoh=342","isLike":0,"isCollect":0,"collectNum":1,"commitNum":1,"likeNum":0},{"postId":61,"content":"想提哦你太太，在这里我咯哦字","createTime":"2018-10-07 16:34:13","author":"xiaoyao","status":1,"circleType":1,"uid":50,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu5xQOAEnBcAAAbMQL38hM027.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu5xQSAJgnkABbkH615RJI824.jpg"}],"authorNickName":"xiaoyao","isLike":0,"isCollect":0,"collectNum":2,"commitNum":1,"likeNum":1},{"postId":66,"content":"Sdfasfasdf","createTime":"2018-10-08 14:13:52","author":"Yyyyyyyyyyy","status":1,"circleType":1,"uid":47,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu69aCAEewQAAAgpofqVPI345.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu69aCATtXCAA_VXmlLS34001.jpg"}],"authorNickName":"Yyyyyyyyyyy","authorAvatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwIISAbrKgABS5bt3vsfQ607.jpg","isLike":0,"isCollect":0,"collectNum":0,"commitNum":1,"likeNum":0}]
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
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
    private List<Blog> list;
    @SerializedName("navigatepageNums")
    private List<Integer> navigatePageNum;

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

    public List<Integer> getNavigatePageNum() {
        return navigatePageNum;
    }

    public void setNavigatePageNum(List<Integer> navigatePageNum) {
        this.navigatePageNum = navigatePageNum;
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

    public List<Blog> getList() {
        return list;
    }

    public void setList(List<Blog> list) {
        this.list = list;
    }

}
