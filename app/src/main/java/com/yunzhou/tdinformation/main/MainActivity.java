package com.yunzhou.tdinformation.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.TabEntity;
import com.yunzhou.tdinformation.community.CommunityFragment;
import com.yunzhou.tdinformation.home.HomeFragment;
import com.yunzhou.tdinformation.lottery.lottery.LotteryFragment;
import com.yunzhou.tdinformation.main.presenter.MainPresenter;
import com.yunzhou.tdinformation.mine.MineFragment;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.blog.GlideSimpleLoader;

import java.util.ArrayList;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.main
 *  @文件名:   MainActivity
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:04
 *  @描述：    主activity
 */

public class MainActivity extends BaseActivity<MainPresenter> implements OnTabSelectListener, ImageWatcherHelper.Provider {
    private static final String TAG = "MainActivity";
    private int[] mIconSelectIds = {
            R.mipmap.ic_button_home_true, R.mipmap.ic_button_lottery_true,
            R.mipmap.ic_button_social_true, R.mipmap.ic_button_center_true};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_button_home_false, R.mipmap.ic_button_lottery_false,
            R.mipmap.ic_button_social_false, R.mipmap.ic_button_center_false};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.home_container)
    FrameLayout mContent;
    @BindView(R.id.bottom_tab)
    CommonTabLayout mBottomTab;

    private HomeFragment mHomeFragment;
    private LotteryFragment mLotteryFragment;
    private CommunityFragment mCommunityFragment;
    private MineFragment mMineFragment;
    private ImageWatcherHelper iwHelper;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createP(Context context) {
        return new MainPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        String[] stringArray = getResources().getStringArray(R.array.bottom);
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader());
        for (int i = 0; i < stringArray.length; i++) {
            mTabEntities.add(new TabEntity(stringArray[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBottomTab.setOnTabSelectListener(this);

        int i = dealWithFragment(savedInstanceState);
        mBottomTab.setTabData(mTabEntities);
        mBottomTab.setCurrentTab(i - 1);
    }

    private int dealWithFragment(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            int pos = getIntent().getIntExtra("position", 1);
            mHomeFragment = HomeFragment.newInstance();
            mLotteryFragment = LotteryFragment.newInstance();
            mCommunityFragment = CommunityFragment.newInstance();
            mMineFragment = MineFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.home_container, mHomeFragment, "1");
            ft.add(R.id.home_container, mLotteryFragment, "2");
            ft.add(R.id.home_container, mCommunityFragment, "3");
            ft.add(R.id.home_container, mMineFragment, "4");
            // show hide 懒加载调用的是hidden
            ft.hide(mHomeFragment);
            ft.hide(mLotteryFragment);
            ft.hide(mCommunityFragment);
            ft.hide(mMineFragment);
            switch (pos) {
                case 1:
                    ft.show(mHomeFragment);
                    break;
                case 2:
                    ft.show(mLotteryFragment);
                    break;
                case 3:
                    ft.show(mCommunityFragment);
                    break;
                case 4:
                    ft.show(mMineFragment);
                    break;
            }
            ft.commit();
            return pos;
        } else {
            int pos = savedInstanceState.getInt("position", 1);
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("1");
            mLotteryFragment = (LotteryFragment) getSupportFragmentManager().findFragmentByTag("2");
            mCommunityFragment = (CommunityFragment) getSupportFragmentManager().findFragmentByTag("3");
            mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("4");

            showCurFragment(pos);
            return pos;
        }
    }

    private void showCurFragment(int pos) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mHomeFragment);
        ft.hide(mLotteryFragment);
        ft.hide(mCommunityFragment);
        ft.hide(mMineFragment);
        switch (pos) {
            case 1:
                ft.show(mHomeFragment);
                break;
            case 2:
                ft.show(mLotteryFragment);
                break;
            case 3:
                ft.show(mCommunityFragment);
                break;
            case 4:
                ft.show(mMineFragment);
                break;
        }
        ft.commit();
    }

    @Override
    public void onTabSelect(int position) {
        mBottomTab.setCurrentTab(position);
        showCurFragment(position + 1);
        Utils.setStatusBarColor(this, position == mIconSelectIds.length - 1 ? Color.parseColor("#FFD86738") :
                        getResources().getColor(android.R.color.white));
    }

    @Override
    public void onTabReselect(int position) {

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra("position", 1);
        context.startActivity(starter);
    }

    /*------------ UI -------------*/
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public ImageWatcherHelper iwHelper() {
        return iwHelper;
    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
