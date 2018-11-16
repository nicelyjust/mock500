package com.yunzhou.tdinformation.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.StackFragmentCommonAct;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.setting.factory.SettingFragmentFactory;
import com.yunzhou.tdinformation.user.UserManager;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   SettingActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:29
 *  @描述：
 */

public class SettingActivity extends StackFragmentCommonAct implements ISettingCallback{
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.shadow)
    View mShadow;
    private SettingFragmentFactory fragmentFactory;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.fl_container;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        fragmentFactory = new SettingFragmentFactory();
        mShadow.setVisibility(View.GONE);
        onSelected(SettingFragmentFactory.MainTag);
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

    /**
     * 回退
     */
    protected void onHome() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            AbsSettingFragment fragment = (AbsSettingFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
            setTitle(fragment.getTitle());
            return;
        }
        finish();
    }

    /**
     * 选择fragment
     *
     * @param tag
     */
    public void onSelected(int tag) {
        final AbsSettingFragment baseFragment = fragmentFactory.createFragment(tag);
        setTitle(baseFragment.getTitle());
        addFragment(baseFragment);
    }
    @Override
    public void onSettingAccountClick() {
        if (UserManager.getInstance().isLogin()){
            onSelected(SettingFragmentFactory.AccountTag);
        } else {
            LoginActivity.start(this);
        }
    }

    @Override
    public void onSettingNoticeClick() {
        if (UserManager.getInstance().isLogin()){
            onSelected(SettingFragmentFactory.MessageTag);
        } else {
            LoginActivity.start(this);
        }
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
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            AbsSettingFragment curFragment = (AbsSettingFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
            if (curFragment instanceof SettingMessageFragment) {
                ((SettingMessageFragment) curFragment).saveSetting();
            } else{
                getSupportFragmentManager().popBackStackImmediate();
                AbsSettingFragment fragment = (AbsSettingFragment) getSupportFragmentManager().findFragmentById(getContainerViewId());
                setTitle(fragment.getTitle());
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context) {
        if (UserManager.getInstance().isLogin()) {
            Intent starter = new Intent(context, SettingActivity.class);
            context.startActivity(starter);
        } else {
            LoginActivity.start(context);
        }

    }
}
