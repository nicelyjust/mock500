package com.yunzhou.tdinformation.community.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunzhou.tdinformation.community.view.CircleFragment;
import com.yunzhou.tdinformation.community.view.FollowFragment;
import com.yunzhou.tdinformation.community.view.GroundFragment;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community
 *  @文件名:   CommunityPagerAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 13:48
 *  @描述：
 */

public class CommunityPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTabs;

    public CommunityPagerAdapter(FragmentManager fm, @NonNull String[] tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (mTabs != null) {
            String tab = mTabs[position];
            if (tab.equals("广场")){
                return GroundFragment.newInstance();
            }else if (tab.equals("圈子")){
                return CircleFragment.newInstance();
            }else if (tab.equals("关注")){
                return FollowFragment.newInstance();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }
}
