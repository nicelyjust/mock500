package com.yunzhou.tdinformation.expert.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert.presenter
 *  @文件名:   IArticlePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 15:29
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.expert.ArticleEntity;

import java.util.List;

public interface IArticlePresenter {
    void loadArticleSuccess(int loadType, List<ArticleEntity.ArticleBean> contents);

    void loadArticleError(int loadType, String msg);
}
