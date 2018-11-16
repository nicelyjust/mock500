package com.yunzhou.tdinformation.community.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.view
 *  @文件名:   GroundView
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 17:10
 *  @描述：    广场和关注公用一个view
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface GroundView extends BaseRefreshView{

    void showLoadMoreBlogData(List<Blog> blog);

    void showBlogDataView(List<Blog> blog);

    void showAdminPost(Blog blog);

    void showAdminPostErrorView();

    void showCollectSuccess(int preIsCollect);

    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);
}
