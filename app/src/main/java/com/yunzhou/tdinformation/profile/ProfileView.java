package com.yunzhou.tdinformation.profile;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.user.UserEntity;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.profile
 *  @文件名:   ProfileView
 *  @创建者:   lz
 *  @创建时间:  2018/11/6 10:32
 *  @描述：
 */

public interface ProfileView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showLoadMoreBlogData(List<Blog> list);

    void showBlogDataView(List<Blog> data);

    void showCollectSuccess(int preIsCollect);

    void showCollectError(int postId);

    void showLikeSuccess(int preIsLike);

    void showLikeError(int postId);

    void showUserInfo(UserEntity entity);

    void showFollowSuccess(boolean hasFollow, String msg);

    void showFollowError(boolean hasFollow, String message);

    void showFollowView(boolean isFollow);
}
