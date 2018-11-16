package com.yunzhou.tdinformation.blog.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   BlogDetailView
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 16:28
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.blog.CommentChildBean;
import com.yunzhou.tdinformation.bean.blog.PostCommentEntity;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface BlogDetailView extends BaseRefreshView{
    void showBlogDataView(List<PostCommentEntity> data, Blog blog);

    void showLoadMoreCommentData(List<PostCommentEntity> postCommentList);

    void showNoCommentsView(Blog blog);

    void showError(int visibility);

    void showCollectSuccess(int preIsCollect);

    void showCollectError(int preIsCollect);

    void showLikeSuccess(int preIsLike);

    void showLikeError();

    void showFollowSuccess();

    void showFollowError(String message);

    void insertLikeCommentOk(int position, int preIsLike);

    void insertLikeCommentError(String message);

    void showAddMainComment(String content);

    /**
     * @param position 位置
     * @param commentChildBean 回复子评论的childBean
     */
    void showAddComment(int position, CommentChildBean commentChildBean);
    /**
     * @param position 位置
     */
    void showAddChildComment(int position);

    void showCommentFailed(String msg);
}
