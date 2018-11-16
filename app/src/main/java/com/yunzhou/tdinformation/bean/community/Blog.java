package com.yunzhou.tdinformation.bean.community;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.community
 *  @文件名:   Blog
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 19:46
 *  @描述：
 */

public class Blog extends Entity {
    /**
     * postId : 48
     * content : 121212
     * createTime : 2018-10-08 11:37:55
     * author : yunzhou_u16or5
     * status : 4
     * circleType : 1
     * uid : 47
     * list : [{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AG7Q8AAAQZby4Qcw663.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AKZl3AAA-o0dRv6U820.jpg"}]
     * authorNickName : Yyyyyyyyyyy
     * authorAvatar : http://39.108.61.68:80/group1/M00/00/06/rBKZOFuwIISAbrKgABS5bt3vsfQ607.jpg
     * isLike : 0
     * isCollect : 0
     * collectNum : 3
     * commitNum : 14
     * likeNum : 2
     */
    // 推荐:0,公告:1
    public int type;
    private int postId;
    private String content;
    private String createTime;
    private String author;
    private int status;
    private int circleType;
    private int uid;
    private String authorNickName;
    private String authorAvatar;
    private int isLike;
    private int isCollect;
    private int collectNum;
    private int commitNum;
    private int likeNum;
    // 用户是否关注
    private int isCheck;

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    @SerializedName("list")
    private List<ImgUrlBean> imgList;
    public List<Uri> urlThumbList = new ArrayList<>(4);
    public List<Uri> urlList = new ArrayList<>(4);

    public List<ImgUrlBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgUrlBean> imgList) {
        this.imgList = imgList;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCircleType() {
        return circleType;
    }

    public void setCircleType(int circleType) {
        this.circleType = circleType;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getCommitNum() {
        return commitNum;
    }

    public void setCommitNum(int commitNum) {
        this.commitNum = commitNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public void init() {
        urlThumbList.clear();
        urlList.clear();
        if (imgList != null) {
            for (ImgUrlBean imgUrlBean : imgList) {
                String smallImg = imgUrlBean.getSmallImg();
                if (!TextUtils.isEmpty(smallImg))
                    urlThumbList.add(Uri.parse(smallImg));
                if (!TextUtils.isEmpty(imgUrlBean.getOriginalImg()))
                    urlList.add(Uri.parse(imgUrlBean.getOriginalImg()));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Blog) {
            if (this == obj) {
                return true;
            }
            return ((Blog) obj).getPostId() == this.getPostId();
        } else {
            return false;
        }
    }
}
