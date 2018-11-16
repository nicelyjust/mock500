package com.yunzhou.tdinformation.login.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.onLogin.presenter
 *  @文件名:   LoginPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 15:12
 *  @描述：
 */

import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.JsonUtil;
import com.yunzhou.common.utils.SpUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.AppManager;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginFragmentFactory;
import com.yunzhou.tdinformation.login.model.LoginModel;
import com.yunzhou.tdinformation.login.view.ILoginView;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.PatternUtils;

public class LoginPresenter extends WrapperPresenter<ILoginView> implements ILoginPresenter {

    private final LoginModel mModel;

    public LoginPresenter() {
        this.mModel = new LoginModel(this);
    }

    public boolean checkPhoneIsValid(String phone) {
        return PatternUtils.checkMobileNumber(phone);
    }

    public void onLogin(int loginType, String phoneNum, String passWord) {
        mView.showLoading();
        mModel.onLogin(loginType, phoneNum, passWord);
    }

    public void onRegister(String phoneNum, String auth, String passWord) {
        mView.showLoading();
        mModel.onRegister(phoneNum, auth, passWord);
    }

    /**
     * 设置新密码
     */
    public void onFindModifyPassword(String phone, String newPsd) {
        mView.showLoading();
        mModel.modifyNewPassword(phone, Base64.encodeToString(newPsd.getBytes(), Base64.NO_WRAP));
    }

    public void onLoginRequestSendAuth(String phoneNum) {
        mModel.onLoginRequestAuth(phoneNum);
    }

    public void onFindPasswordVerifyAuth(String phoneNum, String auth) {
        mModel.onFindPasswordVerifyAuth(phoneNum, auth);
    }

    public void onForgetRequestAuth(String phoneNum) {
        mModel.onForgetSendAuth(phoneNum);
    }


    public void onRegisterSendAuth(String phoneNum) {
        mModel.onRegisterSendAuth(phoneNum);
    }

    @Override
    public void onSendAuthSuccess(int type, String auth) {
        // 验证码发成功
        if (isViewNotNull()) {
        }

    }

    @Override
    public void onSendAuthError(int type, int error) {

    }

    @Override
    public void onSendAuthError(int type, String error) {

    }

    @Override
    public void onLoginSuccess(String jwt, UserEntity userEntity) {
        if (!isViewNotNull()) {
            return;
        }
        // 先保存起来
        UserManager.getInstance().setUserInfo(userEntity);
        UserManager.getInstance().setJwt(jwt);
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).put(AppConst.Extra.USER, JsonUtil.jsonString(userEntity));
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).put(AppConst.Extra.USER_JWT, jwt);
        mView.hideLoading();
        mView.showLoginSuccess();
    }

    @Override
    public void onLoginError(String msg) {
        if (!isViewNotNull()) {
            return;
        }
        mView.showToast(msg);
        mView.hideLoading();
    }

    @Override
    public void onRegisterSuccess() {
        if (!isViewNotNull()) {
            return;
        }
        mView.showRegisterSuccess();
        mView.hideLoading();
    }

    @Override
    public void onRegisterError(String msg) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        ToastUtils.showToastWithBorder(AppManager.getsAppContext(), R.string.net_error, Toast.LENGTH_SHORT, Gravity.TOP);
    }

    @Override
    public void onVerifyAuthSuccess() {
        mView.showVerifyAuthSuccess();
    }

    @Override
    public void onVerifyAuthError() {
        mView.showVerifyAuthError();
    }

    @Override
    public void settingNewPsdSuccess() {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        if (mView.getShowType() == LoginFragmentFactory.ForgetTag) {
            mView.jumpToLoginView();
        } else {
            mView.popFragment();
        }
    }

    @Override
    public void settingNewPsdError(String msg) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        // 简单的show toast
        mView.showToast(msg);
    }

    @Override
    public void destroy() {

    }
}
