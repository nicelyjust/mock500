package com.yunzhou.tdinformation.home.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   HeadLinePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 18:55
 *  @描述：
 */


import android.util.Log;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.model.HeadLineModel;
import com.yunzhou.tdinformation.home.view.HeadLineView;

import java.util.ArrayList;
import java.util.List;

public class HeadLinePresenter extends WrapperPresenter<HeadLineView> implements IHeadLinePresenter {

    private static final String TAG = "HeadLinePresenter";
    private HeadLineModel mModel;
    private List<ContentEntity> mContents;
    private boolean mHasBanner;
    private boolean mHasExpert;

    public HeadLinePresenter() {
        mModel = new HeadLineModel(this);
        mContents = new ArrayList<>();
    }

    @Override
    public void destroy() {

    }

    public void loadBanner() {
        mModel.loadBanner();
    }

    public void loadExperts() {
        mModel.loadExperts();
    }

    public void loadHeadline(int loadType) {
        mModel.loadHeadline(loadType);
    }

    @Override
    public void onHeadLineError(int loadType, String exception) {
        if (isViewNotNull()) {
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    break;
                default:
                    // TODO: 2018/10/3 网络错误
                    break;
            }
        }
    }

    @Override
    public void onHeadLineSuccess(int loadType, List<ContentEntity> contents) {
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (isViewNotNull()) {
                    mView.finishLoadMore(0, true, contents.size() == AppConst.COUNT_SMAll);
                    mView.showLoadMoreHeadLineData(contents);
                }
                mContents.addAll(contents);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN && isViewNotNull()) {
                    mView.finishRefresh(true);
                }
                mContents.clear();
                mContents.addAll(contents);
                if (isViewNotNull())
                    mView.showHeadLineDataView(mContents);
                break;
        }
    }

    @Override
    public void onBannerSuccess(BannerEntity bannerEntity) {
        mHasBanner = true;
        if (isViewNotNull()) mView.showBannerView(bannerEntity);
    }

    @Override
    public void onBannerError(String msg) {
        mHasBanner = false;
        Log.e(TAG, "onBannerError: " + msg);
    }

    @Override
    public void onLoadExpertSuccess(List<ExpertEntity> expertEntities) {
        mHasExpert = true;
        if (isViewNotNull()) mView.showExpertView(expertEntities);
    }

    @Override
    public void onLoadExpertError(String message) {
        mHasExpert = false;
        Log.e(TAG, "onLoadExpertError: " + message);
    }

    public void onLoadMore() {
        mModel.loadHeadline(AppConst.LOAD_TYPE_UP);
    }

    public void onRefresh() {
        if (!mHasBanner)
            loadBanner();
        if (!mHasExpert)
            loadExperts();
        mModel.loadHeadline(AppConst.LOAD_TYPE_DOWN);
    }
}
