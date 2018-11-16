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
import com.yunzhou.tdinformation.bean.lottery.LotteryHistoryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.view.LotteryHistoryView;

import java.util.ArrayList;
import java.util.List;



/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   LotteryHistoryPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 11:26
 *  @描述：
 */

public class LotteryHistoryPresenter extends WrapperPresenter<LotteryHistoryView> {

    private static final String TAG = "LotteryHistoryPresenter";
    private int pageNo = 1;
    private final List<LotteryEntity.LotteryResultDtoBean> mData;

    public LotteryHistoryPresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(final int loadType , int lotteryId) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<LotteryHistoryEntity>>>get(NetConstant.GET_HISTORY_LOTTERY)
                .params(AppConst.Param.LOTTERY_ID, lotteryId)
                .params(AppConst.Param.PAGE_SIZE, 10)
                .params(AppConst.Param.PAGE_NO, pageNo)
                .execute(new JsonCallback<JsonResult<DataBean<LotteryHistoryEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<LotteryHistoryEntity>>> response) {
                        LotteryHistoryEntity historyEntity = response.body().content.dataMap;
                        if (historyEntity != null ) {
                            List<LotteryEntity.LotteryResultDtoBean> lotteryResults = historyEntity.getLotteryResults();
                            dealWithSuccess(loadType, lotteryResults);
                        }
                    }
                    @Override
                    public void onError(Response<JsonResult<DataBean<LotteryHistoryEntity>>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message );
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
                });
    }

    private void dealWithSuccess(int loadType, List<LotteryEntity.LotteryResultDtoBean> lotteryResults) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (lotteryResults != null ) {
                    mView.finishLoadMore(0, true, lotteryResults.size() < 10);
                    mView.showLoadMoreData(lotteryResults);
                    mData.addAll(lotteryResults);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (lotteryResults == null || lotteryResults.isEmpty()){
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mData.clear();
                mData.addAll(lotteryResults);
                mView.showDataView(mData);
                break;
        }
    }

    @Override
    public void destroy() {

    }
}
