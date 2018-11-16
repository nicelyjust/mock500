package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   GeneralCommentBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:11
 *  @描述：
 */

public class GeneralCommentBean extends Entity {
    /**
     * commentId : 638
     * userId : 68
     * avatar : http://39.108.61.68:80/group1/M00/00/08/rBKZOFvAKYSAAYTBAAAbkW-TkKk349.png
     * nickName : yunzhou_dugjb3
     * releaseTime : 1540798997
     * commentContent : 测试哦哦
     * praiseNum : 0
     * replyNum : 0
     * isPraise : 0
     */

    private int commentId;
    private int userId;
    private String avatar;
    private String nickName;
    private long releaseTime;
    private String commentContent;
    private int praiseNum;
    private int replyNum;
    private int isPraise;
    // 0 最新 1 热评
    public int type ;
    // 总数
    public int size ;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeneralCommentBean) {
            if (this == obj) {
                return true;
            }
            return ((GeneralCommentBean) obj).getCommentId() == this.getCommentId();
        } else {
            return false;
        }
    }
}
