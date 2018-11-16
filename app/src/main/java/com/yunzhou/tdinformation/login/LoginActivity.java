package com.yunzhou.tdinformation.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.StackFragmentActivity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.callback.ILoginCallback;
import com.yunzhou.tdinformation.login.fragment.AbstractLoginFlyWeightFragment;
import com.yunzhou.tdinformation.login.fragment.ForgetFragment;
import com.yunzhou.tdinformation.login.fragment.LoginFragment;
import com.yunzhou.tdinformation.login.presenter.LoginPresenter;
import com.yunzhou.tdinformation.login.view.ILoginView;
import com.yunzhou.tdinformation.view.WaitDialog;

import butterknife.BindView;


public class LoginActivity extends StackFragmentActivity<LoginPresenter> implements ILoginCallback, ILoginView {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.shadow)
    View mShadow;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    //享元fragment工厂
    private LoginFragmentFactory fragmentFactory;
    private LoginPresenter mPresenter;
    public static final int AUTH_LENGTH = 4;
    private WaitDialog mDialog;
    private int mShowType = LoginFragmentFactory.LoginTag;

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        fragmentFactory = new LoginFragmentFactory();
        mShadow.setVisibility(View.GONE);
        mShowType = getIntent().getIntExtra(AppConst.Extra.SHOW_TYPE, 0);
        if (mShowType == LoginFragmentFactory.ForgetTag) {
            onSelected(LoginFragmentFactory.ForgetTag);
        } else {
            onSelected(LoginFragmentFactory.LoginTag);
        }
    }
    @Override
    public int getShowType() {
        return this.mShowType;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createP(Context context) {
        mPresenter = new LoginPresenter();
        return mPresenter;
    }

    /**
     * 选择fragment
     *
     * @param tag
     */
    public void onSelected(int tag) {
        final AbstractLoginFlyWeightFragment baseFragment =
                fragmentFactory.createMainFragment(LoginActivity.this, tag);
        setTitle(baseFragment.getTitle());
        addFragment(baseFragment);
    }

    /**
     * 回退
     */
    protected void onHome() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            AbstractLoginFlyWeightFragment fragment = (AbstractLoginFlyWeightFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
            setTitle(fragment.getTitle());
            return;
        }
        finish();
    }

    @Override
    protected int getContainerViewId() {
        return R.id.fl_container;
    }


    @Override
    protected void onResume() {
        super.onResume();
        fragmentFactory.onResume();
    }

    public void onClick(View view) {
    }

    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    public void setTitle(@StringRes int title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }
    public static void startModify(Context context ,int type) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.putExtra(AppConst.Extra.SHOW_TYPE, type);
        context.startActivity(starter);
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }
    public static void startForResult(Context context) {
        Activity activity = (Activity) context;
        Intent starter = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(starter , AppConst.Request.LOGIN);
    }
    @Override
    public void onForgetPassword() {
        onSelected(LoginFragmentFactory.ForgetTag);
    }

    @Override
    public void onRegister() {
        onSelected(LoginFragmentFactory.RegisterTag);
    }

    @Override
    public LoginPresenter getPresenter() {
        return this.mPresenter;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            AbstractLoginFlyWeightFragment fragment = (AbstractLoginFlyWeightFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
            setTitle(fragment.getTitle());
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showLoginSuccess() {
        ToastUtils.showToastWithBorder(this, R.string.login_ok, Toast.LENGTH_SHORT, Gravity.TOP);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(AppConst.Action.REFRESH_ACCOUNT));
        finish();
        setResult(RESULT_OK);
    }

    @Override
    public void showRegisterSuccess() {
        popFragment();
    }

    @Override
    public void showVerifyAuthSuccess() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(getContainerViewId());
        if (fragment != null) {
            if (fragment instanceof ForgetFragment) {
                ForgetFragment forgetFragment = (ForgetFragment) fragment;
                forgetFragment.setCurForgetType(ForgetFragment.TYPE_NEW_PASSWORD);
                setTitle(forgetFragment.getTitle());
            }
        }
    }

    @Override
    public void showVerifyAuthError() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToastWithBorder(this, msg, Toast.LENGTH_SHORT, Gravity.TOP);
    }

    @Override
    public void jumpToLoginView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(getContainerViewId(), new LoginFragment() ,"login");
        transaction.commitAllowingStateLoss();
    }
    @Override
    public void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            AbstractLoginFlyWeightFragment fragment = (AbstractLoginFlyWeightFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
            setTitle(fragment.getTitle());
        }
    }

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new WaitDialog(this);
        }
        mDialog.show();

    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentFactory.onDestroy();
        fragmentFactory = null;
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
