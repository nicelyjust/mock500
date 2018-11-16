package com.yunzhou.tdinformation.mine.payarticle.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.PayArticleEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect.view
 *  @文件名:   PayArticleView
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 15:54
 *  @描述：
 */

public interface PayArticleView extends BaseRefreshView {
    void showLoadMoreData(List<PayArticleEntity.ArticlesBean> data);

    void showDataView(List<PayArticleEntity.ArticlesBean> data);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);
}
