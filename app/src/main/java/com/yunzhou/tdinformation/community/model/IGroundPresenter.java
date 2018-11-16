package com.yunzhou.tdinformation.community.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.model
 *  @文件名:   IGroundPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 17:40
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface IGroundPresenter {
    void loadBlogSuccess(int loadType, List<Blog> list);

    void loadBlogError(int loadType, String msg);

    void loadAdminPostSuccess(Blog blog);

    void loadAdminPostError(String msg);
}
