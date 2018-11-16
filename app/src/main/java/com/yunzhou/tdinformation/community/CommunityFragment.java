package com.yunzhou.tdinformation.community;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment;
import com.yunzhou.tdinformation.community.adapter.CommunityPagerAdapter;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.main
 *  @文件名:   CommunityFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:51
 *  @描述：    社区fragment
 */

public class CommunityFragment extends LazyBaseFragment {
    @BindView(R.id.tab_community)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_community)
    ViewPager mVp;
    private String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        tabs = getResources().getStringArray(R.array.community);
        initVpAndFragments();
        mTab.setViewPager(mVp);
    }

    private void initVpAndFragments() {
        CommunityPagerAdapter communityPagerAdapter = new CommunityPagerAdapter(getChildFragmentManager(), tabs);
        mVp.setAdapter(communityPagerAdapter);
        mVp.setOffscreenPageLimit(2);
    }

    @Override
    protected void fetchData() {

    }

    public static CommunityFragment newInstance() {
        CommunityFragment communityFragment = new CommunityFragment();
        return communityFragment;
    }
}
