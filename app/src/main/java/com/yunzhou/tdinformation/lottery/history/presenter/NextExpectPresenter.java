package com.yunzhou.tdinformation.lottery.history.presenter;

import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.ExpectPredictEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.history.view.NextExpectView;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.presenter
 *  @文件名:   NextExpectPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:16
 *  @描述：
 */

public class NextExpectPresenter extends WrapperPresenter<NextExpectView> {
    private static final String TAG = "NextExpectPresenter";
    private int pageNo = 1;
    private List<ExpectPredictEntity> mData;

    public NextExpectPresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(final int loadType, int lotteryId, String expect) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<List<ExpectPredictEntity>>>>get(NetConstant.NEXT_LOTTERY_PREDICTION + "/" + lotteryId)
                .params(AppConst.Param.EXPECT, expect)
                .execute(new JsonCallback<JsonResult<DataBean<List<ExpectPredictEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<ExpectPredictEntity>>>> response) {
                        if (!isViewNotNull()) {
                            return;
                        }
                        mView.hideLoading();
                        DataBean<List<ExpectPredictEntity>> content = response.body().content;
                        if (content != null) {
                            dealWithSuccess(loadType, content.dataMap);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<ExpectPredictEntity>>>> response) {
                        super.onError(response);
                        if (!isViewNotNull()) {
                            return;
                        }
                        mView.hideLoading();
                        dealWithError(loadType, response.getException().getMessage());
                    }
                });
    }

    private void dealWithError(int loadType, String message) {
        Log.e(TAG, "dealWithError: " + message);
        if (!isViewNotNull()) {
            return;
        }
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

    private void dealWithSuccess(int loadType, List<ExpectPredictEntity> entities) {
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (entities != null) {
                    mView.finishLoadMore(0, true, entities.size() < 10);
                    mView.showLoadMoreData(entities);
                    mData.addAll(entities);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (entities == null || entities.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mData.clear();
                mData.addAll(entities);
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
