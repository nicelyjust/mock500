package com.yunzhou.tdinformation.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment;
import com.yunzhou.tdinformation.home.adapter.HomeAdapter;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.mine.campaign.CampaignActivity;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   HomeFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 18:38
 *  @描述：
 */
public class HomeFragment extends LazyBaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_lucky_bag)
    ImageView mIvLuckyBag;
    @BindView(R.id.tab_home)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_home)
    ViewPager mVp;
    private String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mIvLuckyBag.setOnClickListener(this);
        tabs = getResources().getStringArray(R.array.home_top);
        List<Fragment> fragments = new ArrayList<>(tabs.length);
        initVpAndFragments(fragments);
        mTab.setViewPager(mVp);
    }

    private void initVpAndFragments(List<Fragment> fragments) {
        HeadLineFragment headLineFragment = HeadLineFragment.newInstance();
        LowLotteryFragment lowLotteryFragment = LowLotteryFragment.newInstance();
        LayDetailFragment layDetailFragment = LayDetailFragment.newInstance();
        fragments.add(headLineFragment);
        fragments.add(lowLotteryFragment);
        fragments.add(layDetailFragment);

        HomeAdapter homeAdapter = new HomeAdapter<>(getChildFragmentManager(), tabs, fragments);
        mVp.setAdapter(homeAdapter);
        mVp.setOffscreenPageLimit(2);
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_lucky_bag:
                if (UserManager.getInstance().isLogin())
                    CampaignActivity.start(mContext);
                else
                    LoginActivity.start(mContext);
                break;
            default:
                break;
        }
    }
}
