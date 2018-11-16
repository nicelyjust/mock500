package com.yunzhou.tdinformation.bean.home;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http.bean
 *  @文件名:   HeadLineEntity
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 15:18
 *  @描述：
 */

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;

public class HeadLineEntity extends Entity {
    /**
     * total : 2
     * pages : 1
     * prePage : 0
     * contents : [{"id":16,"title":"日4","author":"xxx","userId":18,"releaseTime":1536893381,"isFreeType":1,"tollAmount":3,"publisherId":null,"source":null,"titleImg":null,"isRecommend":1,"lotteryId":null,"lotteryExcept":null,"intro":null,"viewCount":0,"starCount":0,"rewardModel":null,"status":1,"audit":0,"content":"<p>是大法官<\/p>"},{"id":15,"title":"收费文章第二份","author":"xxx","userId":16,"releaseTime":1536893003,"isFreeType":1,"tollAmount":235,"publisherId":null,"source":"奥术大师网","titleImg":null,"isRecommend":1,"lotteryId":null,"lotteryExcept":null,"intro":null,"viewCount":0,"starCount":0,"rewardModel":null,"status":0,"audit":0,"content":"<p style=\"white-space: normal;\">收费文章第二份<\/p><p style=\"white-space: normal;\">&nbsp;&nbsp;&nbsp;&nbsp;<br/><\/p><p style=\"white-space: normal;\">收费文章第二份<\/p><p style=\"white-space: normal;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费文章第二份<\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color: rgb(255, 0, 0);\">收费文章第二份<\/span><\/p><p><br/><\/p>"}]
     * nextPage : 0
     */

    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private List<ContentEntity> contents;

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

    public List<ContentEntity> getContents() {
        return contents;
    }

    public void setContents(List<ContentEntity> contents) {
        this.contents = contents;
    }


}

