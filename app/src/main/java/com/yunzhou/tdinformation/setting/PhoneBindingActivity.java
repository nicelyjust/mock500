package com.yunzhou.tdinformation.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.FastClickUtil;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.setting.presenter.BindingPresent;
import com.yunzhou.tdinformation.setting.view.IBindingView;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.binding.BindingItem;
import com.yunzhou.tdinformation.view.login.LoginItem;
import com.yunzhou.tdinformation.view.login.OnAuthClickListener;

import butterknife.BindView;

public class PhoneBindingActivity extends BaseActivity<BindingPresent> implements IBindingView, View.OnClickListener, OnAuthClickListener {

    public static final int AUTH_LENGTH = 4;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar tbToolBar;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.tv_phone_binding)
    TextView tvPhoneBinding;
    @BindView(R.id.li_old_phone_binding)
    LoginItem liOldPhoneBinding;
    @BindView(R.id.li_new_phone_binding)
    BindingItem liNewPhoneBinding;
    @BindView(R.id.li_verification_code)
    BindingItem liVerificationCode;
    @BindView(R.id.btn_confirm_binding)
    AppCompatButton btnConfirmBinding;

    private BindingPresent bindingPresent;

    @Override
    protected int getContentView() {
        return R.layout.activity_phone_binding;
    }

    @Override
    protected BindingPresent createP(Context context) {
        bindingPresent = new BindingPresent();
        return bindingPresent;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {

        setSupportActionBar(tbToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("绑定手机号");
        shadow.setVisibility(View.GONE);

        liOldPhoneBinding.setTypeName("旧手机号");
        liOldPhoneBinding.setEditHint("已绑定手机号");

        liNewPhoneBinding.setTypeName("新手机号");
        liNewPhoneBinding.setEditHint("新修改手机号");

        liVerificationCode.setTypeName("验证码");
        liVerificationCode.setEditHint("请输入验证码");

        UserEntity entity = UserManager.getInstance().getUserEntity();
        String phone = entity.getPhone();
        tvPhoneBinding.setText(TextUtils.isEmpty(phone) ? "未绑定" :"*******" + phone.substring(7));
        btnConfirmBinding.setText("确认修改");

        liNewPhoneBinding.setOnAuthClickListener(this);
        btnConfirmBinding.setOnClickListener(this);
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, PhoneBindingActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_confirm_binding) {
            // 检查手机号码,检查验证码,检查密码,检查密码输入是否一致
            if (!bindingPresent.checkPhoneIsValid(liOldPhoneBinding.getEditStr())) {
                ToastUtils.showToastWithBorder(this, R.string.old_phoneNum_error,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px(30));
            } else if (!bindingPresent.checkPhoneIsValid(liNewPhoneBinding.getEditStr())) {
                ToastUtils.showToastWithBorder(this, R.string.new_phoneNum_error,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px(30));
            } else if (!Utils.checkAuth(liVerificationCode.getEditStr())) {
                ToastUtils.showToastWithBorder(this, R.string.invalid_auth_tip,
                        Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px(30));
            } else {
                if (!FastClickUtil.isFastClick())
                    bindingPresent.onBindingPhone(liOldPhoneBinding.getEditStr(), liNewPhoneBinding.getEditStr(), liVerificationCode.getEditStr());
            }
        }
    }

    @Override
    public boolean onAuthClick() {
        String phoneNum = getPhoneNum();
        if (bindingPresent.checkPhoneIsValid(phoneNum)) {
//            Toast.makeText(this, phoneNum, Toast.LENGTH_SHORT).show();
            bindingPresent.onSentIdentifyingCode(phoneNum);
            return true;
        } else {
            ToastUtils.showToastWithBorder(this, R.string.phoneNum_error,
                    Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, TDevice.dip2px(30));
            return false;
        }
    }

    private String getPhoneNum() {
        String editStr = liNewPhoneBinding.getEditStr();
        return editStr;
    }

    @Override
    public void onIdentifyingCodeSuccess() {
        showToast("成功获取验证码");
    }


    @Override
    public void showBindingSuccess() {
        showToast("绑定修改成功");
    }

    @Override
    public void showBindingError(String message) {
        showToast(message);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
