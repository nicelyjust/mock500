package com.yunzhou.tdinformation.home.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.model
 *  @文件名:   LowLotteryModel
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 18:41
 *  @描述：
 */

import android.support.annotation.NonNull;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.HeadLineEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.home.presenter.ILowLotteryPresenter;
import com.yunzhou.tdinformation.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowLotteryModel {
    public static final int TYPE_LOW_LOTTERY = 1;
    public static final int TYPE_LAY_DETAIL = 2;
    private static final String CHANNEL_ID = "parentChannelId";
    private static final String TAG = "LowLotteryModel";
    private final JSONObject mBodyBean;
    private ILowLotteryPresenter mPresenter;
    private int pageNo = 1;
    private int mCurType = 1;

    public LowLotteryModel(@NonNull ILowLotteryPresenter presenter, int type) {
        this.mPresenter = presenter;
        this.mBodyBean = new JSONObject();
        this.mCurType = type;
    }

    public void loadDataInfo(final int loadType) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> param = new HashMap<>(2);
        param.put(AppConst.Param.PAGE_SIZE, String.valueOf(mCurType == TYPE_LAY_DETAIL ? AppConst.COUNT : AppConst.COUNT_SMAll));
        param.put(AppConst.Param.PAGE_NO, String.valueOf(pageNo));

        JSONObject put = null;
        try {
            put = mBodyBean.put("status", 0);
            put = mBodyBean.put(CHANNEL_ID, mCurType);
        } catch (JSONException e) {
            try {
                put = mBodyBean.put("status", 0);
                put = mBodyBean.put(CHANNEL_ID, mCurType);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<DataBean<HeadLineEntity>>>post(Utils.addBasicParams(NetConstant.CONTENT_LIST_URL, param))
                .tag(this)
                .upJson(put)
                .execute(new JsonCallback<JsonResult<DataBean<HeadLineEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<HeadLineEntity>>> response) {
                        JsonResult<DataBean<HeadLineEntity>> body = response.body();
                        DataBean<HeadLineEntity> content = body.content;
                        List<ContentEntity> contents = content.dataMap.getContents();
                        if (contents != null && !contents.isEmpty())
                            mPresenter.onDataInfoSuccess(loadType, contents);
                        else
                            mPresenter.onDataInfoError(loadType, "data 为 null");
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<HeadLineEntity>>> response) {
                        super.onError(response);
                        mPresenter.onDataInfoError(loadType, response.getException().getMessage());
                    }
                });
    }

    public void loadChannelColumn(int columnId) {
        OkGo.<JsonResult<DataBean<List<LotteryChannelEntity>>>>get(NetConstant.GET_CHANNEL_COLUMN)
                .params(AppConst.Param.PARENT_ID, columnId)
                .execute(new JsonCallback<JsonResult<DataBean<List<LotteryChannelEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<LotteryChannelEntity>>>> response) {
                        List<LotteryChannelEntity> entities = response.body().content.dataMap;
                        if (entities != null && !entities.isEmpty()) {
                            mPresenter.loadChannelSuccess(entities);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<LotteryChannelEntity>>>> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: "+ response.getException().getMessage());
                    }
                });
    }

    public void loadChannelLottery(final int pos, int channelId) {
        OkGo.<JsonResult<DataBean<LotteryEntity.LotteryResultDtoBean>>>get(NetConstant.GET_SPECIAL_LOTTERY)
                .params(AppConst.Param.CHANNEL_ID, channelId)
                .execute(new JsonCallback<JsonResult<DataBean<LotteryEntity.LotteryResultDtoBean>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<LotteryEntity.LotteryResultDtoBean>>> response) {
                        LotteryEntity.LotteryResultDtoBean entity = response.body().content.dataMap;
                        if (entity != null) {
                            mPresenter.loadChannelLotterySuccess(pos,entity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<LotteryEntity.LotteryResultDtoBean>>> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: "+ response.getException().getMessage());
                    }
                });
    }
}
