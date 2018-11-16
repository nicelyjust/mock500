package com.yunzhou.tdinformation.home.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   HomeAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 11:44
 *  @描述：
 */

public class HomeAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private String[] mTab;
    private List<T> mFragments;

    public HomeAdapter(FragmentManager fm, @NonNull String[] tab, @NonNull List<T> fragments) {
        super(fm);
        this.mTab = tab;
        this.mFragments = fragments;
    }

    @Override
    public T getItem(int position) {
        return mFragments == null || mFragments.isEmpty() ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTab == null ? 0 : mTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTab == null || mTab.length == 0 ? "" : mTab[position];
    }
}
