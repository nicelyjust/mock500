package com.yunzhou.tdinformation.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;

import java.util.List;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.fragment
 *  @文件名:   StackFragmentCommonAct
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:24
 *  @描述：
 */
public abstract class StackFragmentCommonAct extends BaseCommonAct {
    //当前显示的fragment
    protected BaseFragment currentFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (null != fragments) {
                for (int i = fragments.size() - 1; i >= 0; i--) {
                    Fragment fragment = fragments.get(i);
                    if (fragment instanceof BaseFragment) {
                        currentFragment = (BaseFragment) fragment;
                        break;
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    /**
     * 添加一个fragment 如果一个fragment存在与stack，第一个任务不添加进stack
     * 则清空此fragment以上所有的fragment
     * @param fragment 需要添加的fragment
     */
    protected void addFragment(BaseFragment fragment){
        int stackCount = getSupportFragmentManager().getBackStackEntryCount();
        int size = getSupportFragmentManager().getFragments().size();
        FragmentManager.BackStackEntry backStackEntry;
        BaseFragment baseFragment = findFragmentByTag(fragment.getTag1());
        if(baseFragment != null){
            //先检测栈中是否存在该fragment
            for(int i = 0;i < stackCount - 1;i++){
                backStackEntry = getSupportFragmentManager().getBackStackEntryAt(i);
                if(backStackEntry.getName().equals(baseFragment.getTag1())){
                    //发现目的fragment，移除之上所有fragment
                    backStackEntry = getSupportFragmentManager().getBackStackEntryAt( i + 1);
                    if(backStackEntry != null){
                        getSupportFragmentManager().popBackStackImmediate(backStackEntry.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return;
                    }
                }
            }
            //栈中存在,则判断此fragment为根fragment,清除栈中所有
            if(stackCount > 0){
                backStackEntry = getSupportFragmentManager().getBackStackEntryAt( 0);
                if(backStackEntry != null){
                    getSupportFragmentManager().popBackStackImmediate(backStackEntry.getName(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return;
                }

            }
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(size >= 1){
            fragmentTransaction.setCustomAnimations(R.anim.push_left_in, 0, 0, R.anim.push_right_out);
            fragmentTransaction.addToBackStack(fragment.getTag1());
        }else
            currentFragment = fragment;
        fragmentTransaction.add(getContainerViewId(),fragment,fragment.getTag1());
        fragmentTransaction.commit();
    }
    //View层装载器
    protected abstract int getContainerViewId();

    /**
     * 查找被TAG标注的fragment
     * @param tag 标注
     * @return fragment 可以为空
     */
    @Nullable
    protected BaseFragment findFragmentByTag(String tag){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment == null ? null : (BaseFragment) fragment;
    }
}
