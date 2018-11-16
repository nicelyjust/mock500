package com.yunzhou.tdinformation.community.presenter;

import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.community.CircleItemEntity;
import com.yunzhou.tdinformation.community.view.CircleView;
import com.yunzhou.tdinformation.constant.NetConstant;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.presenter
 *  @文件名:   CirclePresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 14:09
 *  @描述：
 */

public class CirclePresenter extends WrapperPresenter<CircleView> {


    private static final String TAG = "CirclePresenter";

    public CirclePresenter() {
    }

    public void loadCircleData() {
        mView.showLoading();
        OkGo.<JsonResult<DataBean<List<CircleItemEntity>>>>get(NetConstant.GET_CIRCLE_TYPE)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<JsonResult<DataBean<List<CircleItemEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<CircleItemEntity>>>> response) {
                        List<CircleItemEntity> circleItemEntities = response.body().content.dataMap;
                        mView.hideLoading();
                        if (circleItemEntities == null || circleItemEntities.isEmpty()) {
                            mView.showCircleDataError(View.VISIBLE, "data is null");
                        } else {
                            mView.showCircleData(circleItemEntities);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<CircleItemEntity>>>> response) {
                        super.onError(response);
                        mView.hideLoading();
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message);
                        mView.showCircleDataError(View.VISIBLE, message);
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
