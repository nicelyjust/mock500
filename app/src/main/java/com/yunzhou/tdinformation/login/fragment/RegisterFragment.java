package com.yunzhou.tdinformation.login.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.FastClickUtil;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.login.callback.ICallback;
import com.yunzhou.tdinformation.login.presenter.LoginPresenter;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.login.LoginItem;
import com.yunzhou.tdinformation.view.login.OnAuthClickListener;

import butterknife.BindView;

public class RegisterFragment extends AbstractLoginFlyWeightFragment implements View.OnClickListener, OnAuthClickListener {
    @BindView(R.id.li_input_phone_register)
    LoginItem mLiInputPhone;
    @BindView(R.id.li_input_auth_register)
    LoginItem mLiInputAuth;
    @BindView(R.id.li_input_password_register)
    LoginItem mLiInputPassword;
    @BindView(R.id.li_input_password_confirm_register)
    LoginItem mLiInputPasswordConfirm;
    @BindView(R.id.btn_confirm_register)
    AppCompatButton mBtnConfirm;
    private ICallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ICallback) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initWidget(View root) {
        mLiInputPassword.setTypeName(R.string.app_login_password);
        mLiInputPassword.setEditHint(R.string.app_password_limit);
        mLiInputPasswordConfirm.setTypeName(R.string.app_auth_password);
        mLiInputPasswordConfirm.setEditHint(R.string.app_password_confirm);

        mBtnConfirm.setOnClickListener(this);
        mLiInputAuth.setOnAuthClickListener(this);
    }

    @Override
    public int getTitle() {
        return R.string.app_register_title;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm_register) {
            // 检查手机号码,检查验证码,检查密码,检查密码输入是否一致
            if (!mCallback.getPresenter().checkPhoneIsValid(mLiInputPhone.getEditStr())) {
                ToastUtils.showToastWithBorder(getContext(), R.string.phoneNum_error,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
            } else if (!Utils.checkAuth(mLiInputAuth.getEditStr())) {
                ToastUtils.showToastWithBorder(getContext(), R.string.invalid_auth_tip,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
            } else if (!Utils.checkPasswordLength(mLiInputPassword.getEditStr())) {
                ToastUtils.showToastWithBorder(getContext(), R.string.invalid_password_tip,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
            } else if (!Utils.checkPasswordEqual(mLiInputPassword.getEditStr(), mLiInputPasswordConfirm.getEditStr())) {
                ToastUtils.showToastWithBorder(getContext(), R.string.password_no_equal,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
            } else {
                if (!FastClickUtil.isFastClick())
                    mCallback.getPresenter().onRegister(mLiInputPhone.getEditStr(), mLiInputAuth.getEditStr(), mLiInputPassword.getEditStr());
            }
        }
    }

    @Override
    public boolean onAuthClick() {
        // 发送验证码,需先验证手机号
        LoginPresenter presenter = mCallback.getPresenter();
        String phoneNum = getPhoneStr();
        if (presenter.checkPhoneIsValid(phoneNum)) {
            presenter.onRegisterSendAuth(phoneNum);
            return true;
        } else {
            ToastUtils.showToastWithBorder(getContext(), R.string.phoneNum_error,
                    Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px( 30));
            return false;
        }
    }

    private String getPhoneStr() {
        return mLiInputPhone.getEditStr();
    }

}
