package com.yunzhou.tdinformation.home.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.model
 *  @文件名:   HeadLineModel
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 18:59
 *  @描述：
 */

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;
import com.yunzhou.tdinformation.bean.home.HeadLineEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.home.presenter.IHeadLinePresenter;
import com.yunzhou.tdinformation.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class HeadLineModel {
    private static final String TAG = "HeadLineModel";
    private final JSONObject mBodyBean;
    private static final String IS_RECOMMEND = "isRecommend";
    private IHeadLinePresenter mHeadLinePresenter;

    public HeadLineModel(IHeadLinePresenter headLinePresenter) {
        mHeadLinePresenter = headLinePresenter;
        mBodyBean = new JSONObject();
    }

    private int pageNo = 1;

    public void loadExperts() {
        OkGo.<JsonResult<DataBean<List<ExpertEntity>>>>get(NetConstant.GET_RECOMMEND_EXPERT)
                .execute(new JsonCallback<JsonResult<DataBean<List<ExpertEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<ExpertEntity>>>> response) {
                        List<ExpertEntity> expertEntities = response.body().content.dataMap;
                        if (expertEntities != null && !expertEntities.isEmpty())
                            mHeadLinePresenter.onLoadExpertSuccess(expertEntities);
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<ExpertEntity>>>> response) {
                        super.onError(response);
                        mHeadLinePresenter.onLoadExpertError(response.getException().getMessage());
                    }
                });
    }

    public void loadBanner() {
        OkGo.<JsonResult<BannerEntity>>get(NetConstant.GET_BANNER_AND_REPORT)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<JsonResult<BannerEntity>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<BannerEntity>> response) {
                        BannerEntity bannerEntity = response.body().content;
                        mHeadLinePresenter.onBannerSuccess(bannerEntity);
                    }

                    @Override
                    public void onError(Response<JsonResult<BannerEntity>> response) {
                        super.onError(response);
                        mHeadLinePresenter.onBannerError(response.getException().getMessage());
                    }
                });
    }

    public void loadHeadline(final int loadType) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        HashMap<String, String> hashMap = new HashMap<>(2);
        hashMap.put(AppConst.Param.PAGE_SIZE, String.valueOf(AppConst.COUNT_SMAll));
        hashMap.put(AppConst.Param.PAGE_NO, String.valueOf(pageNo));

        JSONObject put = null;
        try {
            put = mBodyBean.put(IS_RECOMMEND, 1);
            put = mBodyBean.put("status", 0);
        } catch (JSONException e) {
            try {
                put = mBodyBean.put("status", 0);
                put = mBodyBean.put(IS_RECOMMEND, 1);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<DataBean<HeadLineEntity>>>post(Utils.addBasicParams(NetConstant.CONTENT_LIST_URL, hashMap))
                .tag(this)
                .upJson(put)
                .execute(new JsonCallback<JsonResult<DataBean<HeadLineEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<HeadLineEntity>>> response) {
                        JsonResult<DataBean<HeadLineEntity>> body = response.body();
                        DataBean<HeadLineEntity> content = body.content;
                        List<ContentEntity> contents = content.dataMap.getContents();
                        if (contents != null && !contents.isEmpty())
                            mHeadLinePresenter.onHeadLineSuccess(loadType, contents);
                        else
                            mHeadLinePresenter.onHeadLineError(loadType, "data 为 null");
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<HeadLineEntity>>> response) {
                        super.onError(response);
                        mHeadLinePresenter.onHeadLineError(loadType, response.getException().getMessage());
                    }
                });
    }

}
