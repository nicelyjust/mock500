package com.yunzhou.tdinformation.mine.red.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectVpAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 13:47
 *  @描述：    
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunzhou.tdinformation.mine.red.view.UseRedFragment;
import com.yunzhou.tdinformation.mine.red.view.UselessRedFragment;

public class MyRedVpAdapter extends FragmentStatePagerAdapter {


    public MyRedVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UseRedFragment.newInstance();
            default:
                return UselessRedFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
