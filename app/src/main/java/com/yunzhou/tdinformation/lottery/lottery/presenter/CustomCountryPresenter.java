package com.yunzhou.tdinformation.lottery.lottery.presenter;

import android.content.Context;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.CountryCustomEntity;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomCountryView;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   CustomCountryPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 10:43
 *  @描述：NATIONWIDE 全国彩 LOCAL 地方彩 HIGH_RATE 高频彩
 */

public class CustomCountryPresenter extends WrapperPresenter<CustomCountryView> {

    private static final String TAG = "CustomCountryPresenter";
    private JSONObject mJsonObject;

    public void loadCountyData() {
        OkGo.<JsonResult<CountryCustomEntity>>get(NetConstant.FIND_CUSTOM_LOTTERY)
                .params(AppConst.Param.USER_ID, UserManager.getInstance().getUid())
                .params(AppConst.Param.LOTTERY_TYPE, "NATIONWIDE")
                .execute(new JsonCallback<JsonResult<CountryCustomEntity>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<CountryCustomEntity>> response) {
                        if (!isViewNotNull()) {
                            return;
                        }
                        CountryCustomEntity entity = response.body().content;
                        List<RecommendLotteryEntity> notSubscribeList = entity.getNotSubscribeList();
                        List<RecommendLotteryEntity> subscribeList = entity.getSubscribeList();
                        if (subscribeList != null && !subscribeList.isEmpty()) {
                            mView.showSubscribeList(subscribeList);
                        }
                        if (notSubscribeList != null && !notSubscribeList.isEmpty()) {
                            mView.showNotSubscribeList(notSubscribeList);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<CountryCustomEntity>> response) {
                        super.onError(response);
                        L.d(response.getException().getMessage());
                        if (!isViewNotNull()) {
                            return;
                        }
                        mView.showErrorView(View.VISIBLE);
                    }
                });
    }

    public void unSubscribe(Context context, final RecommendLotteryEntity item) {
        OkGo.<JsonResult<Object>>delete(NetConstant.CUSTOM_LOTTERY + "/" + UserManager.getInstance().getUid()+"/" + item.getId())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.unSubscribeLotterySuccess(item);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.e(TAG, "onError: " + message);
                        if (isViewNotNull()) {
                            mView.unSubscribeLotteryError(message);
                        }
                    }
                });
    }

    public void subscribe(Context context, final RecommendLotteryEntity item) {
        if (mJsonObject == null) {
            mJsonObject = new JSONObject();
        }
        try {
            mJsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
            mJsonObject.put(AppConst.Param.LOTTERY_ID, item.getId());
        } catch (JSONException e) {
            try {
                mJsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
                mJsonObject.put(AppConst.Param.LOTTERY_ID, item.getId());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.CUSTOM_LOTTERY)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.subscribeLotterySuccess(item);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.e(TAG, "onError: " + message);
                        if (isViewNotNull()) {
                            mView.subscribeLotteryError(message);
                        }
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
