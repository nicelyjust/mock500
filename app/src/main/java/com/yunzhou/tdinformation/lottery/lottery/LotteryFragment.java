package com.yunzhou.tdinformation.lottery.lottery;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment;
import com.yunzhou.tdinformation.home.adapter.HomeAdapter;
import com.yunzhou.tdinformation.lottery.lottery.view.CountryFragment;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomMadeFragment;
import com.yunzhou.tdinformation.lottery.lottery.view.HighFragment;
import com.yunzhou.tdinformation.lottery.lottery.view.LocalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.main
 *  @文件名:   LotteryFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:25
 *  @描述：
 */

public class LotteryFragment extends LazyBaseFragment {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_lottery)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_lottery)
    ViewPager mVp;
    private String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        tabs = getResources().getStringArray(R.array.lottery);
        List<Fragment> fragments = new ArrayList<>(tabs.length);
        initVpAndFragments(fragments);
        mTab.setViewPager(mVp);
        mTvTitle.setText(R.string.lucky_lottery);
    }

    private void initVpAndFragments(List<Fragment> fragments) {
        CountryFragment countryFragment = CountryFragment.newInstance();
        LocalFragment localFragment = LocalFragment.newInstance();
        HighFragment highFragment = HighFragment.newInstance();
        CustomMadeFragment customFragment = CustomMadeFragment.newInstance();
        fragments.add(countryFragment);
        fragments.add(localFragment);
        fragments.add(highFragment);
        fragments.add(customFragment);

        HomeAdapter homeAdapter = new HomeAdapter<>(getChildFragmentManager(), tabs, fragments);
        mVp.setAdapter(homeAdapter);
        mVp.setOffscreenPageLimit(3);
    }

    @Override
    protected void fetchData() {

    }

    public static LotteryFragment newInstance() {
        return new LotteryFragment();
    }

}
