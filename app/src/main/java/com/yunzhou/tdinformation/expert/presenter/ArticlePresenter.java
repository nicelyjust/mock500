package com.yunzhou.tdinformation.expert.presenter;

import android.view.View;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.expert.ArticleEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.expert.model.ArticleModel;
import com.yunzhou.tdinformation.expert.view.ArticleView;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ArticlePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:03
 *  @描述：
 */

public class ArticlePresenter extends WrapperPresenter<ArticleView> implements IArticlePresenter{

    private final ArticleModel mModel;
    private List<ArticleEntity.ArticleBean> mData;

    public ArticlePresenter() {
        mModel = new ArticleModel(this);
        mData = new ArrayList<>();
    }

    public void loadArticle(int loadType, int expertId) {
        mView.showLoading();
        mModel.loadArticle(loadType ,expertId);
    }

    public void onLoadMoreArticle(int loadType, int expertId) {
        loadArticle(loadType ,expertId);
    }

    @Override
    public void loadArticleSuccess(int loadType, List<ArticleEntity.ArticleBean> list) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (list != null ) {
                    mView.finishLoadMore(0, true, list.size() < AppConst.COUNT);
                    mView.showLoadMoreData(list);
                    mData.addAll(list);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (list == null || list.isEmpty()){
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mData.clear();
                mData.addAll(list);
                mView.showDataView(mData);
                break;
        }
    }
    @Override
    public void loadArticleError(int loadType, String msg) {
        if (isViewNotNull()) {
            mView.hideLoading();
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    if (mData.isEmpty())
                        mView.showErrorView(View.VISIBLE);
                    break;
                default:
                    mView.showErrorView(View.VISIBLE);
                    break;
            }
        }
    }
    @Override
    public void destroy() {
        mData.clear();
        mData = null;
    }
}
