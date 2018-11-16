package com.yunzhou.tdinformation.mine.red.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.mine.red.model.IMyRedPacketModel;
import com.yunzhou.tdinformation.mine.red.model.MyRedPacketModel;
import com.yunzhou.tdinformation.mine.red.view.MyRedPacketView;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   MyRedPacketPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 10:20
 *  @描述：
 */

public class MyRedPacketPresenter extends WrapperPresenter<MyRedPacketView> implements IMyRedPacketModel {

    private static final String TAG = "MyRedPacketPresenter";
    private final MyRedPacketModel mModel;
    private List<RedPacksBean> mData;

    public MyRedPacketPresenter() {
        mModel = new MyRedPacketModel(this);
        mData = new ArrayList<>();
    }

    public void loadData(Context context, int loadType) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        mModel.loadRedPacket(context ,loadType , UserManager.getInstance().getUid());
    }

    @Override
    public void loadRedSuccess(int loadType, List<RedPacksBean> list) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (list != null) {
                    mView.finishLoadMore(0, true, list.size() < AppConst.COUNT);
                    mView.showLoadMoreData(list);
                    mData.addAll(list);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                /*if (list == null || list.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }*/
                mData.clear();
                mData.addAll(list);
                mView.showErrorView(View.GONE);
                mView.showDataView(mData);
                break;
        }
    }

    @Override
    public void loadRedError(int loadType, String msg) {
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

    public void loadUselessData(Context context, int loadType) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        mModel.loadUselessRedPacket(context ,loadType , UserManager.getInstance().getUid());
    }
    @Override
    public void destroy() {
        mData.clear();
        mData = null;
    }
}
