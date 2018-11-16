package com.yunzhou.tdinformation.mine.collect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.mine.collect.adapter.CollectVpAdapter;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   PayArticleActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 10:55
 *  @描述：
 */

public class CollectActivity extends BaseCommonAct {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_collect)
    ViewPager mVp;
    private String[] tabs;

    @Override
    protected int getContentView() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("我的收藏");
        tabs = getResources().getStringArray(R.array.collect);
        initVpAndFragments();
        mTab.setViewPager(mVp);
        mTvTitle.setText(R.string.my_collect);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initVpAndFragments() {

        CollectVpAdapter homeAdapter = new CollectVpAdapter(getSupportFragmentManager(), tabs);
        mVp.setAdapter(homeAdapter);
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, CollectActivity.class);
        context.startActivity(starter);
    }
}
