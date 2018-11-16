package com.yunzhou.tdinformation.lottery.lottery.presenter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryProvinceEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.ProvinceItem;
import com.yunzhou.tdinformation.lottery.lottery.view.LocalView;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   LocalPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/23 12:22
 *  @描述：
 */

public class LocalPresenter extends WrapperPresenter<LocalView> {
    // NATIONWIDE 全国 LOCAL 地方 HIGH_RATE 高频
    private static final String LOTTERY_LOCAL = "LOCAL";
    private static final String LOTTERY_HIGH = "HIGH_RATE";
    private static final String TAG = "LocalPresenter";
    public static final int FROM_HIGH = 175;
    public static final int FROM_LOCAL = 176;
    // 暂无分页
    private int pageNo = 1;
    private final ArrayList<MultiItemEntity> mRealEntity;
    private int mFromWhere;

    public LocalPresenter(int fromWhere) {
        this.mFromWhere = fromWhere;
        this.mRealEntity = new ArrayList<>();
    }

    public void loadLottery(final int loadType) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        GetRequest<JsonResult<DataBean<List<LotteryProvinceEntity>>>> getRequest = OkGo.get(NetConstant.GET_LOTTERY);
        if (mFromWhere == FROM_LOCAL) {
            getRequest.params(AppConst.Param.LOTTERY_TYPE, LOTTERY_LOCAL);
        } else{
            getRequest.params(AppConst.Param.LOTTERY_TYPE, LOTTERY_HIGH);
        }
        getRequest.execute(new JsonCallback<JsonResult<DataBean<List<LotteryProvinceEntity>>>>() {
                    private ArrayList<MultiItemEntity> realEntity = new ArrayList<>();
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<LotteryProvinceEntity>>>> response) {
                        List<LotteryProvinceEntity> provinceEntity = response.body().content.dataMap;
                        dealWithLocal(realEntity , provinceEntity, loadType);
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<LotteryProvinceEntity>>>> response) {
                        super.onError(response);
                        dealWithLocalError(loadType, response.getException().getMessage());
                    }

                    @Override
                    public JsonResult<DataBean<List<LotteryProvinceEntity>>> convertResponse(okhttp3.Response response) throws Throwable {
                        JsonResult<DataBean<List<LotteryProvinceEntity>>> jsonResult = super.convertResponse(response);
                        List<LotteryProvinceEntity> dataMap = jsonResult.content.dataMap;
                        int size = dataMap.size();
                        realEntity.clear();
                        for (int j = 0; j < size; j++) {
                            LotteryProvinceEntity provinceEntity = dataMap.get(j);
                            ProvinceItem lv0 = new ProvinceItem(provinceEntity.getId() ,j, provinceEntity.getProvince());
                            List<LotteryEntity> lotteryList = provinceEntity.getLotteryList();
                            int size1 = lotteryList.size();
                            for (int k = 0; k < size1; k++) {
                                lv0.addSubItem(lotteryList.get(k));
                            }
                            realEntity.add(lv0);
                        }
                        return jsonResult;
                    }
                });
    }

    private void dealWithLocal(ArrayList<MultiItemEntity> realEntity, List<LotteryProvinceEntity> provinceEntity, int loadType) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                mView.finishLoadMore(0, true, provinceEntity.size() < AppConst.COUNT);
                mView.showLoadMoreLocalLottery(realEntity);
                mRealEntity.addAll(realEntity);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (provinceEntity == null || provinceEntity.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mRealEntity.clear();
                mRealEntity.addAll(realEntity);
                mView.showLocalLottery(mRealEntity);
                break;
        }
    }

    private void dealWithLocalError(int loadType, String msg) {
        Log.e(TAG, "loadBlogError: " + msg);
        if (isViewNotNull()) {
            mView.hideLoading();
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:
                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    if (mRealEntity.isEmpty())
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
