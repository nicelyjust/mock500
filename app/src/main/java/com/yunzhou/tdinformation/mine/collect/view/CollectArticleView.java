package com.yunzhou.tdinformation.mine.collect.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.CollectArticleEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect.view
 *  @文件名:   CollectArticleView
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 23:42
 *  @描述：
 */

public interface CollectArticleView extends BaseRefreshView {
    void showErrorView(int visible);

    void showDataView(List<CollectArticleEntity.CollectBean> list);

    void showLoadMoreData(List<CollectArticleEntity.CollectBean> list);

    void showEmptyView(int visible);
}
