package com.yunzhou.tdinformation.mine.red;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.mine.red.adapter.MyRedVpAdapter;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   MyRedPacketActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 10:19
 *  @描述：
 */

public class MyRedPacketActivity extends BaseCommonAct implements IRedListener{
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.vp)
    ViewPager mVp;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_red;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText(R.string.my_red_packet);
        MyRedVpAdapter adapter = new MyRedVpAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int currentItem = mVp.getCurrentItem();
            if (currentItem == 0) {
                finish();
            } else {
                mVp.setCurrentItem(0 ,false);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClickCheckUselessRed() {
        mVp.setCurrentItem(1 ,false);
    }
    @Override
    public void onBackPressed() {
        int currentItem = mVp.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            mVp.setCurrentItem(0 ,false);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MyRedPacketActivity.class);
        context.startActivity(starter);
    }
}
