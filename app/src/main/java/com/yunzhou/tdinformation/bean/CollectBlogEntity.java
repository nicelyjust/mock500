package com.yunzhou.tdinformation.bean;

import com.yunzhou.common.http.bean.base.Entity;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   CollectBlogEntity
 *  @创建者:   lz
 *  @创建时间:  2018/11/8 20:01
 *  @描述：
 */

public class CollectBlogEntity extends Entity {


    /**
     * data : [{"postId":159,"content":"来 造作啊","createTime":"2018-11-03 22:55:04","status":1,"circleType":1,"uid":45,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvdtseAK_cnAAAnoKieAV0589.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/0D/rBKZOFvdtseADYY1AAcBkdfP-YE384.jpg"}],"authorNickName":"硬核用户","authorAvatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png","isLike":0,"isCollect":1,"collectNum":1,"commitNum":0,"likeNum":1}]
     * isLastPage : true
     */

    private boolean isLastPage;
    private List<Blog> data;

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public List<Blog> getData() {
        return data;
    }

    public void setData(List<Blog> data) {
        this.data = data;
    }
}
