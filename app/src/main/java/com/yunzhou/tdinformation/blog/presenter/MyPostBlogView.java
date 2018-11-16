package com.yunzhou.tdinformation.blog.presenter;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog.presenter
 *  @文件名:   MyPostBlogView
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 17:03
 *  @描述：
 */

public interface MyPostBlogView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showLoadMoreBlogData(List<Blog> list);

    void showBlogDataView(List<Blog> data);

    void showCollectSuccess(int preIsCollect);

    /**
     * @param postId 点赞失败的postId
     */
    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);

    void showDeleteSuccess(Blog blog);

    void showDeleteError(String message);
}
