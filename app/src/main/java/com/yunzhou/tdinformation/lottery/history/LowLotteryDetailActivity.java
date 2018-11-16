package com.yunzhou.tdinformation.lottery.history;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.adapter.HomeAdapter;
import com.yunzhou.tdinformation.lottery.history.view.DiscussFragment;
import com.yunzhou.tdinformation.lottery.history.view.LowLotteryDetailFragment;
import com.yunzhou.tdinformation.lottery.history.view.NextExpectFragment;
import com.yunzhou.tdinformation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history
 *  @文件名:   LowLotteryDetailActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/29 15:16
 *  @描述：    低频彩开奖详情
 */

public class LowLotteryDetailActivity extends BaseCommonAct {
    @BindView(R.id.tv_title_lottery_detail)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar_lottery_detail)
    Toolbar mToolBar;
    @BindView(R.id.tv_week_lottery)
    TextView mTvWeek;
    @BindView(R.id.tv_time_lottery)
    TextView mTvTime;
    @BindView(R.id.tv_no_lottery)
    TextView mTvNo;
    @BindView(R.id.ll_ball_container_lottery)
    LinearLayout mLlBallContainer;
    @BindView(R.id.tab_lottery_detail)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_lottery_detail)
    ViewPager mVp;
    private LotteryEntity.LotteryResultDtoBean mLotteryResultDtoBean;
    private String mLotteryName;
    private int mLotteryId;
    private String[] tabs;

    @Override
    protected int getContentView() {
        return R.layout.activity_low_lottery_detail;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        mLotteryName = getIntent().getStringExtra(AppConst.Extra.LOTTERY_NAME);
        mLotteryId = getIntent().getIntExtra(AppConst.Param.LOTTERY_ID ,0);
        mLotteryResultDtoBean = getIntent().getParcelableExtra(AppConst.Extra.LOTTERY_RESULT);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText(mLotteryName);
        mTvNo.setText("第"+ mLotteryResultDtoBean.getExpect() + "期");
        mTvTime.setText(mLotteryResultDtoBean.getOpenTime());
        mTvWeek.setText(mLotteryResultDtoBean.getWeekDay());
        setBall(mLlBallContainer, mLotteryResultDtoBean);
        tabs = getResources().getStringArray(R.array.lottery_detail);
        List<Fragment> fragments = new ArrayList<>(tabs.length);
        initVpAndFragments(fragments);
        mTab.setViewPager(mVp);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initVpAndFragments(List<Fragment> fragments) {
        LowLotteryDetailFragment lotteryDetailFragment = LowLotteryDetailFragment.newInstance(mLotteryId, mLotteryResultDtoBean.getExpect());
        NextExpectFragment nextExpectFragment = NextExpectFragment.newInstance(mLotteryId, mLotteryResultDtoBean.getExpect());
        DiscussFragment discussFragment = DiscussFragment.newInstance(mLotteryId, mLotteryResultDtoBean.getExpect());
        fragments.add(lotteryDetailFragment);
        fragments.add(nextExpectFragment);
        fragments.add(discussFragment);
        HomeAdapter homeAdapter = new HomeAdapter<>(getSupportFragmentManager(), tabs, fragments);
        mVp.setAdapter(homeAdapter);
        mVp.setOffscreenPageLimit(2);
    }
    private void setBall(LinearLayout ballContainer, LotteryEntity.LotteryResultDtoBean item) {
        String openCode = item.getOpenCode();
        List<String> stringList = Utils.splitOpenCode(openCode);
        int size = stringList.size();
        String redCode = null;
        String blueCode = null;
        if (size == 1) {
            redCode = stringList.get(0);
        } else if (size > 1) {
            redCode = stringList.get(0);
            blueCode = stringList.get(1);
        }
        ballContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!TextUtils.isEmpty(redCode)) {
            String[] strings = redCode.split(",");
            int length = strings.length;
            for (int i = 0; i < length; i++) {
                TextView view = new TextView(this);
                if (length >= 10) {
                    view.setTextColor(Color.parseColor("#FF333333"));
                    view.setTextSize(18);
                } else {
                    view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setTextSize(13);
                }
                view.setText(strings[i]);
                if (length < 10)
                    view.setBackgroundResource(R.drawable.shape_red_ball);
                view.setGravity(Gravity.CENTER);
                params.rightMargin = 0;
                if (i == 0)
                    params.leftMargin = 0;
                else
                    params.leftMargin = TDevice.dip2px(6);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
        if (!TextUtils.isEmpty(blueCode)) {
            String[] strings = blueCode.split(",");
            for (int i = 0; i < strings.length; i++) {
                TextView view = new TextView(this);
                view.setTextColor(Color.parseColor("#FFFFFFFF"));
                view.setText(strings[i]);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.shape_blue_ball);
                params.rightMargin = 0;
                params.leftMargin = TDevice.dip2px(7);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
    }

    public static void start(Context context, String lotteryName,int lotteryId,LotteryEntity.LotteryResultDtoBean bean) {
        Intent starter = new Intent(context, LowLotteryDetailActivity.class);
        starter.putExtra(AppConst.Extra.LOTTERY_RESULT, bean);
        starter.putExtra(AppConst.Extra.LOTTERY_NAME, lotteryName);
        starter.putExtra(AppConst.Param.LOTTERY_ID, lotteryId);
        context.startActivity(starter);
    }

}
