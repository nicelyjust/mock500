package com.yunzhou.tdinformation.login.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.login.callback.ICallback;
import com.yunzhou.tdinformation.login.presenter.LoginPresenter;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.login.LoginItem;
import com.yunzhou.tdinformation.view.login.OnAuthClickListener;

import butterknife.BindView;


public class ForgetFragment extends AbstractLoginFlyWeightFragment implements View.OnClickListener, OnAuthClickListener {
    @BindView(R.id.li_input_phone_forget)
    LoginItem mLiInputPhone;
    @BindView(R.id.li_input_auth_forget)
    LoginItem mLiInputAuth;
    @BindView(R.id.btn_confirm_forget)
    AppCompatButton mBtnConfirm;
    private ICallback mCallback;

    public static final int TYPE_SEND_AUTH = 0;
    public static final int TYPE_NEW_PASSWORD = 1;

    private int mCurType = TYPE_SEND_AUTH;
    private String mCurPhone;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ICallback) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget;
    }

    @Override
    protected void initWidget(View root) {
        mBtnConfirm.setOnClickListener(this);
        mLiInputAuth.setOnAuthClickListener(this);
    }

    @Override
    public int getTitle() {
        return mCurType == TYPE_SEND_AUTH ? R.string.app_password_title : R.string.app_new_password_title;
    }

    /**
     * trigger 找密码时,点击确定通过之后显示设置新密码界面
     *
     * @param curType TYPE_SEND_AUTH TYPE_NEW_PASSWORD
     */
    public void setCurForgetType(int curType) {
        this.mCurType = curType;
        initUI(mCurType);
    }

    private void initUI(int curType) {
        switch (curType) {
            case TYPE_SEND_AUTH:
                mLiInputPhone.setStyle(LoginItem.INPUT_TYPE_PHONE);
                mLiInputAuth.setStyle(LoginItem.INPUT_TYPE_AUTH);
                //默认hint和typeName所以不需要设置
                break;
            case TYPE_NEW_PASSWORD:
                mLiInputPhone.setStyle(LoginItem.INPUT_TYPE_PASSWORD);
                mLiInputPhone.setTypeName(R.string.app_new_password);
                mLiInputPhone.setEditHint(R.string.app_password_limit);

                mLiInputAuth.setStyle(LoginItem.INPUT_TYPE_PASSWORD);
                mLiInputAuth.setTypeName(R.string.app_confirm_password);
                mLiInputAuth.setEditHint(R.string.app_password_confirm);
                break;
            default:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_forget:

                // 先判断在设置新密码界面还是发送验证码界面
                String editStr = mLiInputAuth.getEditStr();
                if (mCurType == TYPE_SEND_AUTH) {
                    // 检查手机
                    mCurPhone = getFirstLiStr();
                    if (!mCallback.getPresenter().checkPhoneIsValid(mCurPhone)) {
                        ToastUtils.showToastWithBorder(getContext(), R.string.phoneNum_error, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                        return;
                    }
                    //检查验证码格式
                    if (editStr.length() != LoginActivity.AUTH_LENGTH) {
                        ToastUtils.showToastWithBorder(getContext(), R.string.invalid_auth_tip, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                    } else {
                        mCurPhone = getFirstLiStr();
                        mCallback.getPresenter().onFindPasswordVerifyAuth(mCurPhone,editStr);
                    }

                } else if (mCurType == TYPE_NEW_PASSWORD) {
                    if (Utils.checkPasswordEqual(getFirstLiStr(), editStr))
                        mCallback.getPresenter().onFindModifyPassword(mCurPhone, editStr);
                    else
                        ToastUtils.showToastWithBorder(getContext(), R.string.password_no_equal, Toast.LENGTH_LONG, Gravity.BOTTOM);
                }
                break;
            default:

                break;
        }

    }

    @Override
    public boolean onAuthClick() {
        // 发送验证码,需先验证手机号
        LoginPresenter presenter = mCallback.getPresenter();
        String phoneNum = getFirstLiStr();
        if (presenter.checkPhoneIsValid(phoneNum)) {
            presenter.onForgetRequestAuth(phoneNum);
            return true;
        } else {
            ToastUtils.showToastWithBorder(getContext(), R.string.phoneNum_error, Toast.LENGTH_SHORT, Gravity.BOTTOM);
            return false;
        }
    }

    private String getFirstLiStr() {
        return mLiInputPhone.getEditStr();
    }
}
