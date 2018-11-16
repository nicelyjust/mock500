package com.yunzhou.tdinformation.web;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ExpertPageController
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 19:05
 *  @描述：
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.PayJwtEntity;
import com.yunzhou.tdinformation.bean.oder.OderEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

public class WebDetailController {
    private static final String TAG = "ExpertPageController";
    private WebDetailView mView;
    private OderEntity mContent;

    public WebDetailController(WebDetailView view) {
        mView = view;
    }

    public @Nullable OderEntity getContent() {
        return mContent;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }

    }

    public boolean isViewNotNull() {
        return mView != null;
    }

    public void addOder(Context context, String contentID, String money) {
        mView.showLoading();
        String jwt = UserManager.getInstance().getJwt();
        JSONObject put = new JSONObject();
        try {
            put.put(AppConst.Param.BIZ_ID, Integer.parseInt(contentID));
            put.put(AppConst.Param.ORDER_MONEY, Double.parseDouble(money));
            put.put(AppConst.Param.RESOURCE_TYPE, AppConst.Param.RESOURCE_TYPE_ARTICLE);

        } catch (JSONException e) {
            try {
                put.put(AppConst.Param.BIZ_ID, contentID);
                put.put(AppConst.Param.ORDER_MONEY, money);
                put.put(AppConst.Param.RESOURCE_TYPE, AppConst.Param.RESOURCE_TYPE_ARTICLE);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<OderEntity>>post(NetConstant.ADD_ODER_POST)
                .headers(AppConst.Param.JWT, jwt)
                .upJson(put)
                .execute(new LoginCallback<JsonResult<OderEntity>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<OderEntity>> response) {
                        if (!isViewNotNull())
                            return;
                        mView.hideLoading();
                        OderEntity content = response.body().content;
                        if (content != null) {
                            mContent = content;
                            mView.showOderDialog(mContent);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<OderEntity>> response) {
                        super.onError(response);
                        if (!isViewNotNull())
                            return;
                        mView.hideLoading();
                        try {
                            String msg = response.body().metaBean.msg;
                            mView.showToast(msg);
                        } catch (Exception e) {
                            try {
                                String message = response.getException().getMessage();
                                mView.showToast(message);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                mView.showToast(R.string.net_error);
                            }
                        }
                    }
                });
    }

    public void getPayJwt(Context context) {
        mView.showBtnLoading();
        OkGo.<JsonResult<PayJwtEntity>>post(NetConstant.ADD_PAY_JWT)
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .params(AppConst.Param.APP_ID , TDevice.getUniquePsuedoID())
                .params(AppConst.Param.UID , UserManager.getInstance().getUid())
                .execute(new LoginCallback<JsonResult<PayJwtEntity>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<PayJwtEntity>> response) {
                        if (!isViewNotNull()){
                            return;
                        }
                        mView.hideBtnLoading();
                        String payJWT = response.body().content.getPayJWT();
                        if (!TextUtils.isEmpty(payJWT)) {
                            // 弹出输入密码界面
                            mView.showGetJwtOk(payJWT);
                        }else {
                            mView.showToast(R.string.data_error);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<PayJwtEntity>> response) {
                        super.onError(response);
                        if (!isViewNotNull()){
                            return;
                        }
                        mView.hideBtnLoading();
                        mView.showToast(R.string.data_error);
                    }
                });
    }

    public void pay(Context context, String orderNum, String result, String payJWT, String orderMoney) {
        JSONObject put = new JSONObject();
        try {
            put.put(AppConst.Param.ORDER_NUM, orderNum);
            put.put("moneyNum", orderMoney);
            put.put(AppConst.Param.ORDER_PASSWORD, Base64.encodeToString(result.getBytes(), Base64.NO_WRAP));

        } catch (JSONException e) {
            try {
                put.put(AppConst.Param.ORDER_NUM, orderNum);
                put.put("moneyNum", orderMoney);
                put.put(AppConst.Param.ORDER_PASSWORD, Base64.encodeToString(result.getBytes(), Base64.NO_WRAP));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.PAY)
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .headers(AppConst.Param.PAY_JWT , payJWT)
                .upJson(put)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        mView.showPaySuccess();
                    }
                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        try {
                            String message = response.getException().getMessage();
                            mView.showToast(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.showToast(R.string.net_error);
                        }
                    }
                });
    }
}
