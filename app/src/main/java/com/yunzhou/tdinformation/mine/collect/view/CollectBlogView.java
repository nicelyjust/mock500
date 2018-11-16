package com.yunzhou.tdinformation.mine.collect.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectBlogView
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 14:16
 *  @描述：
 */

public interface CollectBlogView extends BaseRefreshView {

    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showCollectSuccess(int preIsCollect);

    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);

    void showLoadMoreBlogData(List<Blog> list);

    void showBlogDataView(List<Blog> data);
}
