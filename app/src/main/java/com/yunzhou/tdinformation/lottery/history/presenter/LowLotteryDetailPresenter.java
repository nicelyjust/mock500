package com.yunzhou.tdinformation.lottery.history.presenter;

import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.LotteryDetailEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.history.view.LowLotteryDetailView;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.presenter
 *  @文件名:   LowLotteryDetailPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 10:10
 *  @描述：
 */

public class LowLotteryDetailPresenter extends WrapperPresenter<LowLotteryDetailView> {

    private static final String TAG = "LowLotteryDetailP";

    public void loadData(final int loadType, int lotteryId, String expect) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL) {
            mView.showLoading();
        }
        OkGo.<JsonResult<DataBean<LotteryDetailEntity>>>get(NetConstant.GET_LOTTERY_DETAIL)
                .params(AppConst.Param.LOTTERY_ID, lotteryId)
                .params(AppConst.Param.EXPECT, expect)
                .execute(new JsonCallback<JsonResult<DataBean<LotteryDetailEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<LotteryDetailEntity>>> response) {
                        if (!isViewNotNull()) {
                            return;
                        }
                        mView.hideLoading();
                        LotteryDetailEntity dataMap = response.body().content.dataMap;
                        if (dataMap != null) {
                            dealWithSuccess(loadType, dataMap);
                        } else {
                            mView.showEmptyView(View.VISIBLE,true);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<LotteryDetailEntity>>> response) {
                        super.onError(response);
                        if (!isViewNotNull()) {
                            return;
                        }
                        mView.hideLoading();
                        dealWithError(loadType, response.getException().getMessage());
                    }
                });
    }

    private void dealWithSuccess(int loadType, LotteryDetailEntity detailEntity) {
        List<LotteryDetailEntity.BonusSituationDtoListBean> entities = detailEntity.getBonusSituationDtoList();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(true);
                if (entities == null || entities.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE,false);
                } else {
                    mView.showDataSuccess(detailEntity);
                }
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                if (entities == null || entities.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE ,true);
                } else {
                    mView.showDataSuccess(detailEntity);
                }
                break;
            default:

                break;
        }
    }

    private void dealWithError(int loadType, String message) {
        Log.e(TAG, "dealWithError: " + message);
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(false);
                mView.showErrorView(View.VISIBLE ,false);
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                mView.showErrorView(View.VISIBLE ,true);
                break;
            default:

                break;
        }
    }

    @Override
    public void destroy() {

    }
}
