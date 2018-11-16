package com.yunzhou.tdinformation.mine.campaign;

import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.ActivityEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.campaign
 *  @文件名:   CampaignPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 12:54
 *  @描述：
 */

public class CampaignPresenter extends WrapperPresenter<CampaignView> {
    private static final String TAG = "CampaignPresenter";
    private int pageNo = 1;
    private List<ActivityEntity.ActivityBean> mData;

    public CampaignPresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(final int loadType) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<ActivityEntity>>>get(NetConstant.GET_CAMPAIGN)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .params(AppConst.Param.PAGE_NO, pageNo)
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_MAX)
                .execute(new JsonCallback<JsonResult<DataBean<ActivityEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<ActivityEntity>>> response) {
                        ActivityEntity dataMap = response.body().content.dataMap;
                        if (dataMap != null) {
                            loadDataSuccess(loadType, dataMap.getList());
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<ActivityEntity>>> response) {
                        super.onError(response);
                        loadDataError(loadType, response.getException().getMessage());
                    }
                });
    }

    private void loadDataError(int loadType, String msg) {
        Log.e(TAG, "loadBlogError: " + msg);
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

    private void loadDataSuccess(int loadType, List<ActivityEntity.ActivityBean> list) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (list != null) {
                    mView.finishLoadMore(0, true, list.size() < AppConst.COUNT_SMAll);
                    mView.showLoadMoreData(list);
                    mData.addAll(list);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (list == null || list.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mData.clear();
                mData.addAll(list);
                mView.showErrorView(View.GONE);
                mView.showDataView(mData);
                break;
        }
    }

    @Override
    public void destroy() {
        mData.clear();
        mData = null;
    }
}
