package com.yunzhou.tdinformation.lottery.lottery.presenter;

import android.content.Context;
import android.util.Log;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.lottery.lottery.model.CustomMadeModel;
import com.yunzhou.tdinformation.lottery.lottery.model.ICustomMadeModel;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomMadeView;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   CustomMadePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 17:44
 *  @描述：
 */

public class CustomMadePresenter extends WrapperPresenter<CustomMadeView> implements ICustomMadeModel {

    private static final String TAG = "CustomMadePresenter";
    private final CustomMadeModel mModel;

    public CustomMadePresenter() {
        mModel = new CustomMadeModel(this);
    }

    public void loadCustomLottery(Context context, int loadType) {
        mModel.loadCustomLottery(context, loadType, UserManager.getInstance().getUid());
    }

    public void loadRecommendLottery(int loadType) {
        mModel.loadRecommendLottery(loadType, UserManager.getInstance().getUid());
    }

    @Override
    public void loadCustomLotterySuccess(int loadType, List<SubscribeLotteryEntity> entities) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(true);
                if (entities == null || entities.isEmpty()) {
                    mView.showCustomLotteryEmpty(true);
                } else {
                    mView.showCustomLottery(entities);
                }
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                if (entities == null || entities.isEmpty()) {
                    mView.showCustomLotteryEmpty(true);
                } else {
                    mView.showCustomLottery(entities);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void loadCustomLotteryError(int loadType, String message) {
        Log.e(TAG, "loadCustomLotteryError: ");
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(false);
                mView.showCustomLotteryEmpty(false);
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                mView.showCustomLotteryEmpty(false);
                break;
            default:

                break;
        }
    }

    @Override
    public void loadRecommendLotterySuccess(int loadType, List<RecommendLotteryEntity> entities) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(true);
                if (entities == null || entities.isEmpty()) {
                    mView.showRecommendLotteryEmpty(true);
                } else {
                    mView.showRecommendLottery(entities);
                }
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                if (entities == null || entities.isEmpty()) {
                    mView.showRecommendLotteryEmpty(true);
                } else {
                    mView.showRecommendLottery(entities);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void loadRecommendLotteryError(int loadType, String message) {
        Log.e(TAG, "loadRecommendLotteryError: " + message);
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(false);
                mView.showRecommendLotteryEmpty(false);
                break;
            case AppConst.LOAD_TYPE_NORMAL:
                mView.showRecommendLotteryEmpty(false);
                break;
            default:

                break;
        }
    }

    public void subscribeLottery(Context context, int lotteryId) {
        mView.showLoading();
        mModel.subscribeLottery(context, lotteryId);
    }

    @Override
    public void subscribeLotterySuccess() {
        if (isViewNotNull()) {
            mView.hideLoading();
            mView.showSubscribeLotterySuccess();
        }
    }

    @Override
    public void subscribeLotteryError(String message) {
        if (isViewNotNull()) {
            mView.hideLoading();
            mView.showSubscribeLotteryError(message);
        }
    }

    @Override
    public void destroy() {

    }
}
