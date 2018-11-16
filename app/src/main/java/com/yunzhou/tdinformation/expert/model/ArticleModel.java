package com.yunzhou.tdinformation.expert.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert.model
 *  @文件名:   ArticleModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 15:27
 *  @描述：    TODO
 */

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.bean.expert.ArticleEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.expert.presenter.IArticlePresenter;

import java.util.List;

public class ArticleModel {
    private static final String TAG = "ArticleModel";
    private IArticlePresenter mPresenter;
    private int pageNo = 1;

    public ArticleModel(IArticlePresenter presenter) {
        mPresenter = presenter;
    }

    public void loadArticle(final int loadType, int expertId) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<ArticleEntity>>>get(NetConstant.GET_EXPERT_ARTICLE + "/" + expertId + "/article")
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<JsonResult<DataBean<ArticleEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<ArticleEntity>>> response) {
                        if (mPresenter == null) {
                            return;
                        }
                        ArticleEntity entity = response.body().content.dataMap;
                        if (entity != null) {
                            List<ArticleEntity.ArticleBean> contents = entity.getContents();
                            mPresenter.loadArticleSuccess(loadType , contents);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<ArticleEntity>>> response) {
                        super.onError(response);

                        if (mPresenter == null) {
                            return;
                        }
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message);
                        mPresenter.loadArticleError(loadType , message);
                    }
                });
    }
}
