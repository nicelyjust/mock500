package com.yunzhou.tdinformation.blog.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   IBlogListPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 14:21
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;

public interface IBlogListPresenter {
    void loadBlogSuccess(int loadType, List<Blog> list);

    void loadBlogError(int loadType, String msg);
}
