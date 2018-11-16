package com.yunzhou.tdinformation.blog.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   BlogListView
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 13:37
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface BlogListView extends BaseRefreshView{
    void showLoadMoreBlogData(List<Blog> list);

    void showEmptyView(int visibility);

    void showBlogDataView(List<Blog> blogList);

    void showErrorView(int visibility);

    void showCollectSuccess(int preIsCollect);

    /**
     * @param postId 点赞失败的postId
     */
    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);
}
