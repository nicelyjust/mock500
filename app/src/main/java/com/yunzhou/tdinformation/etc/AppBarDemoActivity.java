package com.yunzhou.tdinformation.etc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;

import java.util.Arrays;

import butterknife.BindView;


/*
 *  @项目名：  mock500
 *  @包名：    com.yunzhou.tdinformation.etc
 *  @文件名:   AppBarDemoActivity
 *  @创建者:   lz
 *  @创建时间:  2019/1/29 14:42
 *  @描述：AppBarLayout如何通俗理解它的scrollFlags,first建立一个认知,系统的知识都是抽象的,
 *  如果我们一开始从抽象的角度去理解可能会产生困惑,尝试用你已熟悉的知识体系(通俗)去理解学习新知识往往会事半功倍
 *  向上滚动是exit,向下滚动是enter
 *  scroll: view会跟随滚动时间一起移动
 *  enterAlways: 向下滚动过程中,不会RV的滚动,该view会直接滚动
 *  enterAlwaysCollapsed: 与enterAlways联用,在未达到最小高度前,产生与enterAlways一样的效果,达到最小高度后响应RV的滚动事件,最后滑动该view
 *  exitUntilCollapsed：向上滚动时达到最小高度,然后再响应Rv的滚动事件
 */
public class AppBarDemoActivity extends BaseCommonAct {
    @BindView(R.id.tb_tool_bar_expert_page)
    Toolbar mToolBar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private SimpleAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_appbar;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AppBarDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SimpleAdapter(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mAdapter.setData(Arrays.asList("C","Java","C++","JS","Python","Kotlin","C","Java","C++","JS","Python","Kotlin"));
    }
}
