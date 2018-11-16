package com.yunzhou.tdinformation.lottery.lottery.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.model
 *  @文件名:   CustomMadeModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 17:44
 *  @描述：
 */

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CustomMadeModel {
    private static final String TAG = "CustomMadeModel";
    private ICustomMadeModel mModelListener;

    public CustomMadeModel(ICustomMadeModel modelListener) {
        mModelListener = modelListener;
    }

    /**
     * 获取已定制的彩种
     *
     * @param loadType 刷新 跟正常加载的
     */
    public void loadCustomLottery(Context context, final int loadType, int uid) {
        OkGo.<JsonResult<DataBean<List<SubscribeLotteryEntity>>>>get(NetConstant.CUSTOM_LOTTERY)
                .params(AppConst.Param.USER_ID, uid)
                .execute(new LoginCallback<JsonResult<DataBean<List<SubscribeLotteryEntity>>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<SubscribeLotteryEntity>>>> response) {
                        List<SubscribeLotteryEntity> entities = response.body().content.dataMap;
                        if (mModelListener != null) {
                            mModelListener.loadCustomLotterySuccess(loadType, entities);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<SubscribeLotteryEntity>>>> response) {
                        super.onError(response);
                        if (mModelListener != null) {
                            mModelListener.loadCustomLotteryError(loadType, response.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * 获取推荐
     *
     * @param loadType
     * @param uid
     */
    public void loadRecommendLottery(final int loadType, int uid) {
        OkGo.<JsonResult<DataBean<List<RecommendLotteryEntity>>>>get(NetConstant.RECOMMEND_CUSTOM_LOTTERY)
                .params(AppConst.Param.USER_ID, uid)
                .execute(new JsonCallback<JsonResult<DataBean<List<RecommendLotteryEntity>>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<RecommendLotteryEntity>>>> response) {
                        List<RecommendLotteryEntity> entities = response.body().content.dataMap;
                        if (mModelListener != null) {
                            mModelListener.loadRecommendLotterySuccess(loadType, entities);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<RecommendLotteryEntity>>>> response) {
                        super.onError(response);
                        if (mModelListener != null) {
                            mModelListener.loadRecommendLotteryError(loadType, response.getException().getMessage());
                        }
                    }
                });
    }

    public void subscribeLottery(Context context, int lotteryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
            jsonObject.put(AppConst.Param.LOTTERY_ID, lotteryId);
        } catch (JSONException e) {
            try {
                jsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
                jsonObject.put(AppConst.Param.LOTTERY_ID, lotteryId);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.CUSTOM_LOTTERY)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(jsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (mModelListener != null) {
                            mModelListener.subscribeLotterySuccess();
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message);
                        if (mModelListener != null) {
                            mModelListener.subscribeLotteryError(message);
                        }
                    }
                });

    }
}
