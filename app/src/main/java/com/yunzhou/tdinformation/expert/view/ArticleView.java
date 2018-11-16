package com.yunzhou.tdinformation.expert.view;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ArticleView
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:03
 *  @描述：
 */

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.expert.ArticleEntity;

import java.util.List;

public interface ArticleView extends BaseRefreshView{
    void showLoadMoreData(List<ArticleEntity.ArticleBean> data);

    void showEmptyView(int visibility);

    void showDataView(List<ArticleEntity.ArticleBean> data);

    void showErrorView(int visibility);
}
