package com.yunzhou.tdinformation.bean.blog;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.blog
 *  @文件名:   PostCommentEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/12 9:55
 *  @描述：
 */

public class PostCommentEntity extends Entity {
    /**
     * commentsId : 457
     * uid : 48
     * content : Asdfasdfasdf adds
     * status : 1
     * createTime : 2018-10-10 11:38:29
     * likeTime : 0
     * postId : 48
     * floor : 10
     * nickName : Asdfasdfasdfasd
     * avatar : http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym-AYi72ABK1BJ5YzDg569.jpg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym6AMwBFAAAu5hFH8YQ321.jpg
     * isLiked : 0
     * childList : []
     * updateTime : 2018-09-30 17:18:20
     */

    private int commentsId;
    private int uid;
    private String content;
    private int status;
    private String createTime;
    @SerializedName("likeNumss")
    private int likeNum;
    private int postId;
    private int floor = -1;
    private String nickName;
    private String avatar;
    private int isLiked;
    private String updateTime;
    @SerializedName("childList")
    private List<CommentChildBean> commentBeans;

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public List<CommentChildBean> getCommentBeans() {
        return commentBeans;
    }

    public void setCommentBeans(List<CommentChildBean> commentBeans) {
        this.commentBeans = commentBeans;
    }

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
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

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
