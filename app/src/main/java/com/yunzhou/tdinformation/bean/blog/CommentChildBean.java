package com.yunzhou.tdinformation.bean.blog;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.blog
 *  @文件名:   CommentChildBean
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 19:32
 *  @描述：
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.tdinformation.utils.span.SpanUtils;

public class CommentChildBean implements Comparable<CommentChildBean>{

    /**
     * childId : 300
     * childContent : 好多好多好
     * childUid : 47
     * childName : 小熊猫
     * childTargetId : 47
     * childTarget : 小熊猫
     */
    // 评论在数据库的主键
    private int childId;
    @SerializedName("childContent")
    private String commentContent;
    @SerializedName("childUid")
    private int childUserId;
    @SerializedName("childName")
    private String childUserName;
    @SerializedName("childTargetId")
    private int parentUserId = -1;
    @SerializedName("childTarget")
    private String parentUserName;
    /**
     * 根节点uid
     */
    public int rootUid;
    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(int childUserId) {
        this.childUserId = childUserId;
    }

    public String getChildUserName() {
        return childUserName;
    }

    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName;
    }

    public int getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(int parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    /**
     * 富文本内容
     */
    private SpannableStringBuilder commentContentSpan;

    public SpannableStringBuilder getCommentContentSpan() {
        return commentContentSpan;
    }

    public void build(Context context) {
        if (parentUserId == -1) {
            commentContentSpan = SpanUtils.makeSingleCommentSpan(context,
                    TextUtils.isEmpty(childUserName) ? "无名学霸" : childUserName, commentContent);
        } else {
            commentContentSpan = SpanUtils.makeReplyCommentSpan(context,
                    TextUtils.isEmpty(parentUserName) ? "无名学霸" : parentUserName,
                    TextUtils.isEmpty(childUserName) ? "无名学霸" : childUserName, commentContent);
        }
    }

    @Override
    public int compareTo(@NonNull CommentChildBean o) {
        return this.childId - o.childId;
    }
}
