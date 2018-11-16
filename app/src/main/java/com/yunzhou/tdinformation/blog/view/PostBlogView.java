package com.yunzhou.tdinformation.blog.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   PostBlogView
 *  @创建者:   lz
 *  @创建时间:  2018/10/19 11:51
 *  @描述：
 */

import android.content.Context;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.blog.BlogPostBean;

public interface PostBlogView extends BaseView{
    void showUploadImgSuccess();

    void showUploadImgError(int length, int failedCount);

    Context getContext();

    void showRealPostError(BlogPostBean blogPostBean);

    void showRealPostSuccess();

    void showWaitDialog(String tips);

    void showMessage(String msg);
}
