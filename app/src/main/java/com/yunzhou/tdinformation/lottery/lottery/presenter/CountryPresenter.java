package com.yunzhou.tdinformation.lottery.lottery.presenter;

import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.view.CountryView;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   CountryPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 16:06
 *  @描述：
 */

public class CountryPresenter extends WrapperPresenter<CountryView> {

    private static final String TAG = "CountryPresenter";
    private final List<LotteryEntity> mData;

    public CountryPresenter() {
        mData = new ArrayList<>();
    }

    @Override
    public void destroy() {

    }

    public void loadCountryLottery(final int loadType) {
        mView.showLoading();
        OkGo.<JsonResult<DataBean<List<LotteryEntity>>>>get(NetConstant.GET_COUNTRY_LOTTERY)
                .execute(new JsonCallback<JsonResult<DataBean<List<LotteryEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<LotteryEntity>>>> response) {
                        List<LotteryEntity> lotteryEntities = response.body().content.dataMap;
                        if (lotteryEntities != null && !lotteryEntities.isEmpty()) {
                            dealWithSuccess(loadType, lotteryEntities);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<LotteryEntity>>>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message);
                        dealWithError(loadType, message);
                    }
                });
    }

    private void dealWithSuccess(int loadType, List<LotteryEntity> lotteryEntities) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(true);
                break;
            default:
                break;
        }
        mData.clear();
        mData.addAll(lotteryEntities);
        mView.showLotteryData(lotteryEntities);
    }

    private void dealWithError(int loadType, String message) {
        Log.e(TAG, "dealWithError: " + message);
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(false);
                if (mData.isEmpty())
                    mView.showErrorView(View.VISIBLE);
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                mView.showErrorView(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
