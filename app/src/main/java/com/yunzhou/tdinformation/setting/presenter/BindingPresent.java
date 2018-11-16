package com.yunzhou.tdinformation.setting.presenter;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.setting.model.BindingModel;
import com.yunzhou.tdinformation.setting.view.IBindingView;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.PatternUtils;

/**
 * Created by Administrator on 2018/11/7.
 */

public class BindingPresent extends WrapperPresenter<IBindingView> implements IBindingPresent {

    BindingModel bindingModel;
    private static final String TAG = "BindingPresent";

    @Override
    public void destroy() {

    }

    public BindingPresent() {
        bindingModel = new BindingModel(this);
    }

    public boolean checkPhoneIsValid(String phoneNum) {
        return PatternUtils.checkMobileNumber(phoneNum);
    }

    public void onSentIdentifyingCode(String phoneNum) {
        bindingModel.onSentIdentifyingCode(phoneNum);
    }

    @Override
    public void onSendIdentifyingCodeSuccess(String msg) {
        mView.onIdentifyingCodeSuccess();
    }

    @Override
    public void onSendIdentifyingCodeError(int StringResId) {

    }

    @Override
    public void onSendIdentifyingCodeError(String msg) {

    }

    public void onBindingPhone(String oldPhoneNum, String newPhoneNum, String identifyingCode) {
//        bindingModel.onBindingPhone(oldPhoneNum,newPhoneNum,identifyingCode);
        OkGo.<JsonResult<Object>>post(NetConstant.BINDING_URL + UserManager.getInstance().getUid() + "/updatePhone")
                .headers(AppConst.Param.JWT,UserManager.getInstance().getJwt())
                .params(AppConst.Param.ID, UserManager.getInstance().getUid())
                .params(AppConst.Param.OLD_PHONE_NUM, oldPhoneNum)
                .params(AppConst.Param.NEW_PHONE_NUM, newPhoneNum)
                .params(AppConst.Param.V_AUTH_CODE, identifyingCode)
                .tag(this)
                .execute(new JsonCallback<JsonResult<Object>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.showBindingSuccess();
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        Log.d(TAG, "onError: " + message);
                        mView.showBindingError(message);
                    }
                });
    }
}
