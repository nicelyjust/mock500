package com.yunzhou.tdinformation.login.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.login.callback.ILoginCallback;
import com.yunzhou.tdinformation.login.presenter.LoginPresenter;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.login.LoginItem;
import com.yunzhou.tdinformation.view.login.OnAuthClickListener;

import butterknife.BindView;


public class LoginFragment extends AbstractLoginFlyWeightFragment implements View.OnClickListener, OnAuthClickListener {

    public static final String TAG = "LoginFragment";
    public static final int LOGIN_TYPE_WORD = 2;
    public static final int LOGIN_TYPE_AUTH = 1;

    public int mCurLoginType = LOGIN_TYPE_WORD;

    @BindView(R.id.tv_auth)
    TextView mTvAuth;
    @BindView(R.id.tv_forget)
    TextView mTvForget;
    @BindView(R.id.bt_confirm)
    AppCompatButton mBtConfirm;
    @BindView(R.id.bt_register)
    AppCompatButton mBtRegister;
    @BindView(R.id.in_input_phone)
    LoginItem mInPhone;
    @BindView(R.id.in_input_password)
    LoginItem mInPassword;
    private ILoginCallback mILoginCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mILoginCallback = (ILoginCallback) context;
    }

    @Override
    protected void initWidget(View root) {
        mTvAuth.setText(R.string.app_auth_login);

        mTvAuth.setOnClickListener(this);
        mTvForget.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
        mInPassword.setOnAuthClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_auth:
                if (mCurLoginType == LOGIN_TYPE_WORD) {
                    mCurLoginType = LOGIN_TYPE_AUTH;
                    mTvAuth.setText(R.string.app_password_login);
                    mInPassword.setStyle(LoginItem.INPUT_TYPE_AUTH);
                } else if (mCurLoginType == LOGIN_TYPE_AUTH) {
                    mCurLoginType = LOGIN_TYPE_WORD;
                    mTvAuth.setText(R.string.app_auth_login);
                    mInPassword.setStyle(LoginItem.INPUT_TYPE_PASSWORD);
                }
                break;
            case R.id.tv_forget:
                mILoginCallback.onForgetPassword();
                break;
            case R.id.bt_confirm:
                String phoneNum = getPhoneNum();

                if (!mILoginCallback.getPresenter().checkPhoneIsValid(phoneNum)) {
                    ToastUtils.showToastWithBorder(getContext(),
                            R.string.phoneNum_error,
                            Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
                } else {
                    String passWord = getPassWord();
                    // 判断密码或者验证码
                    if (mCurLoginType == LOGIN_TYPE_WORD) {
                        if (!Utils.checkPasswordLength(passWord)) {
                            ToastUtils.showToastWithBorder(getContext(), R.string.invalid_password_tip,
                                    Toast.LENGTH_SHORT, Gravity.BOTTOM);
                            return;
                        }
                    } else if (mCurLoginType == LOGIN_TYPE_AUTH) {
                        //已发送验证码
                        if (passWord.length() != LoginActivity.AUTH_LENGTH) {
                            ToastUtils.showToastWithBorder(getContext(), R.string.invalid_auth_tip,
                                    Toast.LENGTH_SHORT, Gravity.BOTTOM);
                            return;
                        }

                    }

                    mILoginCallback.getPresenter().onLogin(mCurLoginType, getPhoneNum(), getPassWord());
                }

                break;
            case R.id.bt_register:
                mILoginCallback.onRegister();
                break;

        }
    }

    private String getPhoneNum() {
        return mInPhone.getEditStr();
    }

    private String getPassWord() {
        return mInPassword.getEditStr();
    }

    @Override
    public int getTitle() {
        return R.string.app_login_title;
    }

    @Override
    public boolean onAuthClick() {
        // 发送验证码,需先验证手机号
        LoginPresenter presenter = mILoginCallback.getPresenter();
        String phoneNum = getPhoneNum();
        if (presenter.checkPhoneIsValid(phoneNum)) {
            presenter.onLoginRequestSendAuth(phoneNum);
            return true;
        } else {
            ToastUtils.showToastWithBorder(getContext(), R.string.phoneNum_error, Toast.LENGTH_SHORT, Gravity.BOTTOM);
            return false;
        }
    }
}
