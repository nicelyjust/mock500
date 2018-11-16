package com.yunzhou.tdinformation.community.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.view
 *  @文件名:   FollowView
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 11:23
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface FollowView extends BaseRefreshView{
    void showLoadMoreBlogData(List<Blog> blog);

    void showBlogDataView(List<Blog> blog);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showCollectSuccess(int preIsCollect);

    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);
}
