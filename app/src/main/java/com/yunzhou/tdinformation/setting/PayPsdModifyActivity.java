package com.yunzhou.tdinformation.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.setting.presenter.PsdModifyPresent;
import com.yunzhou.tdinformation.setting.view.PsdModifyView;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.pay.PayPwdView;
import com.yunzhou.tdinformation.view.pay.PwdInputMethodView;

import butterknife.BindView;
import butterknife.OnClick;

public class PayPsdModifyActivity extends BaseActivity<PsdModifyPresent> implements PsdModifyView, PayPwdView.InputCallBack {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar tbToolBar;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.payPwdView)
    PayPwdView payPwdView;
    @BindView(R.id.btn_confirm_modify)
    AppCompatButton btnConfirmModify;
    @BindView(R.id.inputMethodView)
    PwdInputMethodView inputMethodView;
    @BindView(R.id.tv_password_tips)
    TextView tvPasswordTips;
    private static final int OLD_PASSWORD = 0;
    private static final int NEW_PASSWORD = 1;
    private static final int CONFIRM_PASSWORD = 2;
    int recordStep = 0;
    private String oldStr;
    private String newStr;
    private String confirmStr;
    private PsdModifyPresent psdModifyPresent;
    private int mOldPayPassword;

    @Override
    protected int getContentView() {
        return R.layout.activity_pay_psd_modify;
    }

    @Override
    protected PsdModifyPresent createP(Context context) {
        psdModifyPresent = new PsdModifyPresent();
        return psdModifyPresent;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setSupportActionBar(tbToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("修改支付密码");
        shadow.setVisibility(View.GONE);
        mOldPayPassword = UserManager.getInstance().getUserEntity().getAlreadySetPayPass();
        if (mOldPayPassword == 1) {
            tvPasswordTips.setText("请输入原支付密码，已验证身份");
            inputMethodView.setVisibility(View.VISIBLE);
        } else {
            recordStep++;
            tvPasswordTips.setText("请设置支付密码");
            inputMethodView.setVisibility(View.VISIBLE);
        }


        payPwdView.setInputMethodView(inputMethodView);
        payPwdView.setInputCallBack(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_confirm_modify)
    public void onViewClicked() {
        btnConfirmModify.setEnabled(false);
        switch (recordStep) {
            case OLD_PASSWORD:
                recordStep++;
                payPwdView.clearResult();
                tvPasswordTips.setText("请输入新密码");
                inputMethodView.setVisibility(View.VISIBLE);
                break;
            case NEW_PASSWORD:
                recordStep++;
                payPwdView.clearResult();
                tvPasswordTips.setText(mOldPayPassword != 1 ? "请再次确认支付密码" : "请再次确认新密码");
                inputMethodView.setVisibility(View.VISIBLE);
                btnConfirmModify.setText("完成");
                break;
            case CONFIRM_PASSWORD:
                if (TextUtils.isEmpty(payPwdView.getInputText())) {
                    ToastUtil.showShort(this, "请输入确认密码");
                    btnConfirmModify.setEnabled(false);
                } else if (!payPwdView.getInputText().equals(newStr)) {
                    btnConfirmModify.setEnabled(false);
                    ToastUtil.showShort(this, "前后密码不一致，请重新输入");
                    confirmStr = null;
                    payPwdView.clearResult();
                } else {
                    psdModifyPresent.onPsdModify(mOldPayPassword != 1 ? null : oldStr, confirmStr);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void onInputFinish(String result) {
        switch (recordStep) {
            case OLD_PASSWORD:
                oldStr = result;
                btnConfirmModify.setEnabled(true);
                break;
            case NEW_PASSWORD:
                newStr = result;
                btnConfirmModify.setEnabled(true);
                break;
            case CONFIRM_PASSWORD:
                btnConfirmModify.setEnabled(true);
                confirmStr = result;
                break;
            default:
                break;
        }
    }

    @Override
    public void onModifySuccess() {
        ToastUtil.showShort(this, "修改成功");
        UserEntity userEntity = UserManager.getInstance().getUserEntity();
        userEntity.setAlreadySetPayPass(1);
        UserManager.getInstance().save(userEntity);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onModifyError(String message) {
        ToastUtil.showShort(this, "修改失败,请重试");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public static void startForResult(Activity activity) {
        Intent intent = new Intent(activity, PayPsdModifyActivity.class);
        activity.startActivityForResult(intent, AppConst.Request.MODIFY_PAY_PASSWORD);
    }
}
