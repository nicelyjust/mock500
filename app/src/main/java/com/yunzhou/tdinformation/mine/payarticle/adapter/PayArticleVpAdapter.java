package com.yunzhou.tdinformation.mine.payarticle.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectVpAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 13:47
 *  @描述：    
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunzhou.tdinformation.mine.payarticle.view.PayArticleFragment;
import com.yunzhou.tdinformation.mine.payarticle.view.ReadArticleFragment;

public class PayArticleVpAdapter extends FragmentStatePagerAdapter {

    private String[] mTabs;

    public PayArticleVpAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        String tab = mTabs[position];
        switch (tab) {
            case "已付费":
                return PayArticleFragment.newInstance();
            default:
                return ReadArticleFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs == null ? null : mTabs[position];
    }
}
