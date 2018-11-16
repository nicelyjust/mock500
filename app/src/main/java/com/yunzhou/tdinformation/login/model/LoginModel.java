package com.yunzhou.tdinformation.login.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.onLogin
 *  @文件名:   LoginModel
 *  @创建者:   lz
 *  @创建时间:  2018/9/28 14:56
 *  @描述：
 */

import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yunzhou.common.http.LzException;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.user.SmsAuthJson;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.login.fragment.LoginFragment;
import com.yunzhou.tdinformation.login.presenter.ILoginPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoginModel {
    private static final String TAG = "LoginModel";
    public static final int LOGIN = 0x01;
    public static final int FORGET = 0x02;
    public static final int REGISTER = 0x03;
    public static final int THIRD = 0x04;


    @IntDef({LOGIN, FORGET, REGISTER ,THIRD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SendAuthType {
    }

    private ILoginPresenter mPresenter;

    private int count;

    public LoginModel(ILoginPresenter presenter) {
        this.mPresenter = presenter;
    }

    public void onLogin(int loginType, String phoneNum, String passWord) {
        PostRequest<JsonResult<DataBean<UserEntity>>> postRequest = OkGo.<JsonResult<DataBean<UserEntity>>>post(NetConstant.LOGIN_URL)
                .params(AppConst.Param.CLIENT_TYPE, "APP")
                .params(AppConst.Param.LOGIN_TYPE, loginType)
                .params(AppConst.Param.PHONE_NUM, phoneNum)
                .params(AppConst.Param.APP_ID, TDevice.getUniquePsuedoID());
        if (loginType == LoginFragment.LOGIN_TYPE_WORD) {
            postRequest.params(AppConst.Param.PASSWORD, Base64.encodeToString(passWord.trim().getBytes(), Base64.NO_WRAP));
            postRequest.params(AppConst.Param.V_AUTH_CODE, "");
        } else {
            postRequest.params(AppConst.Param.PASSWORD, "");
            postRequest.params(AppConst.Param.V_AUTH_CODE, passWord);
        }
        postRequest.execute(new JsonCallback<JsonResult<DataBean<UserEntity>>>() {
            @Override
            public void onSuccess(Response<JsonResult<DataBean<UserEntity>>> response) {
                JsonResult<DataBean<UserEntity>> body = response.body();
                mPresenter.onLoginSuccess(body.content.jwt, body.content.dataMap);
            }

            @Override
            public void onError(Response<JsonResult<DataBean<UserEntity>>> response) {
                super.onError(response);
                if (response != null) {
                    mPresenter.onLoginError(response.getException().getMessage());
                }
            }
        });
    }

    public void onRegister(String phoneNum, String auth, String passWord) {
        OkGo.<JsonResult<DataBean<UserEntity>>>post(NetConstant.REGISTER_URL)
                .params(AppConst.Param.PHONE_NUM, phoneNum)
                .params(AppConst.Param.V_AUTH_CODE, auth)
                .params(AppConst.Param.PASSWORD, Base64.encodeToString(passWord.trim().getBytes(), Base64.NO_WRAP))
                .params(AppConst.Param.APP_ID, TDevice.getUniquePsuedoID())
                .execute(new JsonCallback<JsonResult<DataBean<UserEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<UserEntity>>> response) {
                        mPresenter.onRegisterSuccess();
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<UserEntity>>> response) {
                        super.onError(response);
                        mPresenter.onRegisterError(response.getException().getMessage());
                    }
                });

    }

    /**
     * @param phone  手机号
     * @param newPsd base64加密后字符串
     */
    public void modifyNewPassword(final String phone, final String newPsd) {
        OkGo.<JsonResult<Object>>post(NetConstant.UPDATE_LOGIN_PASSWORD)
                .params(AppConst.Param.PHONE_NUM, phone)
                .params(AppConst.Param.PASSWORD, newPsd)
                .execute(new JsonCallback<JsonResult<Object>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        count = 0;
                        mPresenter.settingNewPsdSuccess();
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (count < 3) {
                            modifyNewPassword(phone, newPsd);
                            count++;
                        } else {
                            Throwable exception = response.getException();
                            mPresenter.settingNewPsdError(exception instanceof LzException ? exception.getMessage() : "网络错误,请重试");
                        }

                    }
                });
    }

    public void onLoginRequestAuth(String phoneNum) {
        onSendAuth(LOGIN, phoneNum);
    }

    public void onFindPasswordVerifyAuth(String phoneNum, String auth) {
        OkGo.<JsonResult<SmsAuthJson>>post(NetConstant.VERIFY_AUTH_URL)
                .params(AppConst.Param.PHONE_NUM, phoneNum)
                .params(AppConst.Param.AUTH_CODE, auth)
                .tag(this)
                .execute(new JsonCallback<JsonResult<SmsAuthJson>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<SmsAuthJson>> response) {
                        SmsAuthJson authJson = response.body().content;
                        if (authJson.getRet().getCode() == 200) {
                            mPresenter.onVerifyAuthSuccess();
                        } else {
                            Log.e(TAG, "code error: ");
                            mPresenter.onVerifyAuthError();
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<SmsAuthJson>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.e(TAG, "onError: " + message);
                        mPresenter.onVerifyAuthError();
                    }
                });
    }

    public void onForgetSendAuth(String phoneNum) {
        onSendAuth(FORGET, phoneNum);
    }

    public void onRegisterSendAuth(String phoneNum) {
        onSendAuth(REGISTER, phoneNum);
    }

    private void onSendAuth(final @SendAuthType int type, String phoneNum) {
        PostRequest<JsonResult<SmsAuthJson>> request = OkGo.<JsonResult<SmsAuthJson>>post(NetConstant.SEND_AUTH_URL)
                .params(AppConst.Param.PHONE_NUM, phoneNum);
        if (type == REGISTER)
            request.params(AppConst.Param.REGISTER, 1);
        request.tag(this)
                .execute(new JsonCallback<JsonResult<SmsAuthJson>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<SmsAuthJson>> response) {
                        String auth = response.body().content.getRet().getMsg();
                        if (TextUtils.isEmpty(auth) || auth.trim().length() != LoginActivity.AUTH_LENGTH)
                            mPresenter.onSendAuthError(type, R.string.data_error);
                        else
                            mPresenter.onSendAuthSuccess(type, auth);
                    }

                    @Override
                    public void onError(Response<JsonResult<SmsAuthJson>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.d(TAG, "onError: " + message);
                        mPresenter.onSendAuthError(type, message);
                    }
                });
    }
}
