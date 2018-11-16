package com.yunzhou.tdinformation.setting.model;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.user.SmsAuthJson;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.setting.PhoneBindingActivity;
import com.yunzhou.tdinformation.setting.presenter.IBindingPresent;
import com.yunzhou.tdinformation.user.UserManager;

/**
 * Created by wsj on 2018/11/7.
 */

public class BindingModel {
    private static final String TAG = "BindingModel";
    private final IBindingPresent bindingPresent;

    public BindingModel(IBindingPresent bindingPresent) {
        this.bindingPresent = bindingPresent;
    }

    public void onSentIdentifyingCode(String phoneNum) {
        sentIdentifyingCode(phoneNum);
    }

    private void sentIdentifyingCode(String phoneNum) {
        OkGo.<JsonResult<SmsAuthJson>>post(NetConstant.SEND_AUTH_URL)
                .params(AppConst.Param.PHONE_NUM, phoneNum)
                .tag(this)
                .execute(new JsonCallback<JsonResult<SmsAuthJson>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<SmsAuthJson>> response) {
                        String msg = response.body().content.getRet().getMsg();
                        if (TextUtils.isEmpty(msg) || msg.trim().length() != PhoneBindingActivity.AUTH_LENGTH)
                            bindingPresent.onSendIdentifyingCodeError(R.string.data_error);
                        else
                            bindingPresent.onSendIdentifyingCodeSuccess(msg);
                    }

                    @Override
                    public void onError(Response<JsonResult<SmsAuthJson>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.d(TAG, "onError: " + message);
                        bindingPresent.onSendIdentifyingCodeError(message);
                    }
                });
    }

    public void onBindingPhone(String oldPhoneNum, String newPhoneNum, String identifyingCode) {
        OkGo.<JsonResult<Object>>post(NetConstant.BINDING_URL)
                .params(AppConst.Param.ID, UserManager.getInstance().getUid())
                .params(AppConst.Param.OLD_PHONE_NUM, oldPhoneNum)
                .params(AppConst.Param.NEW_PHONE_NUM, newPhoneNum)
                .params(AppConst.Param.V_AUTH_CODE, identifyingCode)
                .tag(this)
                .execute(new JsonCallback<JsonResult<Object>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {

                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                    }
                });
    }
}
