package com.yunzhou.tdinformation.mine.collect.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.CollectArticleEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.mine.collect.view.CollectArticleView;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectBlogPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 14:15
 *  @描述：
 */

public class CollectArticlePresenter extends WrapperPresenter<CollectArticleView> {

    private static final String TAG = "CollectArticlePresenter";
    private int pageNo = 1;
    private List<CollectArticleEntity.CollectBean> mData;
    public CollectArticlePresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(Context context, final int loadType) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<CollectArticleEntity>>>get(NetConstant.GET_MY_COLLECT +UserManager.getInstance().getUid()+"/1")
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT)
                .execute(new LoginCallback<JsonResult<DataBean<CollectArticleEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<CollectArticleEntity>>> response) {
                        CollectArticleEntity dataMap = response.body().content.dataMap;
                        dealWithSuccess(dataMap ,loadType);
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<CollectArticleEntity>>> response) {
                        super.onError(response);
                        loadArticleError(loadType , response.getException().getMessage());
                    }
                });

    }

    private void dealWithSuccess(CollectArticleEntity entity, int loadType) {
        if (!isViewNotNull()) {
            return;
        }
        List<CollectArticleEntity.CollectBean> list = entity.getList();
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
                mView.showDataView(list);
                break;
        }
    }

    private void loadArticleError(int loadType, String msg) {
        Log.e(TAG, "loadArticleError: " + msg);
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

    }
}
