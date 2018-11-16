package com.yunzhou.tdinformation.view.login;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.R;

import java.lang.ref.WeakReference;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.view
 *  @文件名:   LoginItem
 *  @创建者:   lz
 *  @创建时间:  2018/9/27 20:00
 *  @描述：1.长度限制11,类型设置;
 *  2.除了验证码形式,其余监听文字长度显示清除按钮;
 *  3.验证码形式下,发送验证码及其重新发送时的UI处理,并暴露出点击事件;
 */

public class LoginItem extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "LoginItem";
    private static final int PASSWORD_LENGTH = 16;
    private static final int AUTH_LENGTH = 4;
    public static final int SEND = 0;
    public static final int UN_SEND = 1;
    private int mAuthStatus = UN_SEND;

    /**
     * 对外暴露
     * @return 验证码发送情况
     */
    public int getAuthStatus() {
        return this.mAuthStatus;
    }

    // 手机样式
    public static final int INPUT_TYPE_PHONE = 0;
    // 密码样式
    public static final int INPUT_TYPE_PASSWORD = 1;
    // 验证码样式
    public static final int INPUT_TYPE_AUTH = 2;

    public int mCurType = INPUT_TYPE_PHONE;
    TextView mTvTypeName;
    EditText mEtInput;
    ImageButton mIbClearLogin;
    TextView mTvSendAuth;
    private Context mContext;
    private TimeCount mTimeCount;
    private OnAuthClickListener mOnAuthClickListener;

    public LoginItem(Context context) {
        this(context, null);
    }

    public LoginItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginItem);
        mCurType = typedArray.getInt(R.styleable.LoginItem_li_style, INPUT_TYPE_PHONE);
        typedArray.recycle();
        initView();
        initListener();
        initStyle(mCurType);
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.include_login_item, this);
        mTvTypeName = findViewById(R.id.tv_type_name);
        mEtInput = findViewById(R.id.et_input);
        mIbClearLogin = findViewById(R.id.ib_clear_login);
        mTvSendAuth = findViewById(R.id.tv_send_auth);
    }

    private void initListener() {
        mTvSendAuth.setOnClickListener(this);
        mIbClearLogin.setOnClickListener(this);
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                L.d(TAG, s + "; start: " + start + "; count: " + count + "; after: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                L.d(TAG, s + "; start: " + start + "; before: " + before + "; count: " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                L.d(TAG, s.toString());
                if (s.length() > 0 && mCurType != INPUT_TYPE_AUTH) {
                    setClearBtnVisibility(VISIBLE);
                } else if (s.length() == 0) {
                    setClearBtnVisibility(GONE);
                }

            }
        });
    }

    public void setStyle(int type) {
        this.mCurType = type;
        initStyle(mCurType);
    }

    private void initStyle(int type) {
        switch (type) {
            case INPUT_TYPE_PHONE:
                mTvTypeName.setText(R.string.app_phone);
                mEtInput.setHint(R.string.app_input_phone);
                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                setAuthVisibility(GONE);
                setClearBtnVisibility(GONE);
                break;
            case INPUT_TYPE_PASSWORD:
                mTvTypeName.setText(R.string.app_password);
                mEtInput.setHint(R.string.app_input_password);
                mEtInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                mEtInput.setKeyListener(passWordLimit);
                mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PASSWORD_LENGTH)});
                setAuthVisibility(GONE);
                setClearBtnVisibility(GONE);
                break;
            case INPUT_TYPE_AUTH:
                mTvTypeName.setText(R.string.app_auth);
                mEtInput.setHint(R.string.app_input_auth);
                mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mEtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(AUTH_LENGTH)});
                setAuthVisibility(VISIBLE);
                setClearBtnVisibility(GONE);
                if (mTimeCount == null) {
                    mTimeCount = new TimeCount(this, 60 * 1000, 1000);
                } else {
                    mTimeCount.cancel();
                }
                break;
            default:

                break;
        }
        mEtInput.setText("");
    }

    private NumberKeyListener passWordLimit = new NumberKeyListener() {
        @NonNull
        @Override
        protected char[] getAcceptedChars() {
            return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".toCharArray();
        }

        @Override
        public int getInputType() {
            return InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT;
        }
    };

    public void setTypeName(CharSequence s) {
        if (mTvTypeName != null) {
            mTvTypeName.setText(s);
        }
    }

    public void setTypeName(@StringRes int id) {
        if (mTvTypeName != null) {
            mTvTypeName.setText(id);
        }
    }
    public void setEditHint(CharSequence s) {
        if (mEtInput != null) {
            mEtInput.setHint(s);
        }
    }
    public void setEditHint(@StringRes int id) {
        if (mEtInput != null) {
            mEtInput.setHint(id);
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_send_auth) {
            if (mOnAuthClickListener.onAuthClick()) {
                mTvSendAuth.setEnabled(false);
                // TODO: 2018/9/28 有点问题 异步请求发送验证码,可能没这么回来 最好回来再set发送状态
                mAuthStatus = SEND;
                mTvSendAuth.setText("60".concat(mContext.getResources().getString(R.string.app_resend_auth)));
                mTimeCount.start();
            }

        } else if (v.getId() == R.id.ib_clear_login) {
            mEtInput.setText("");
        }
    }

    private void setClearBtnVisibility(int visibility) {
        if (mIbClearLogin != null) {
            mIbClearLogin.setVisibility(visibility);
        }
    }

    private void setAuthVisibility(int visibility) {
        if (mTvSendAuth != null) {
            mTvSendAuth.setVisibility(visibility);
        }
    }

    /**
     * @param onAuthClickListener 注入监听器
     */
    public void setOnAuthClickListener(OnAuthClickListener onAuthClickListener){
        this.mOnAuthClickListener = onAuthClickListener;
    }
    public String getEditStr() {
        return mEtInput == null ? "" : mEtInput.getText().toString();
    }

    static class TimeCount extends CountDownTimer {
        private final WeakReference<LoginItem> mReference;

        public TimeCount(LoginItem loginItem, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mReference = new WeakReference<>(loginItem);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            LoginItem loginItem = mReference.get();
            if (loginItem == null) {
                return;
            }
            loginItem.mTvSendAuth.setText(String.valueOf(millisUntilFinished / 1000).
                    concat(loginItem.mContext.getResources().getString(R.string.app_resend_auth)));
        }

        @Override
        public void onFinish() {
            LoginItem loginItem = mReference.get();
            if (loginItem == null) {
                return;
            }
            loginItem.mTvSendAuth.setText(R.string.app_send_auth);
            //重新获取验证码
            loginItem.mTvSendAuth.setEnabled(true);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mTimeCount)
            mTimeCount.cancel();
        mTimeCount = null;
    }
}
