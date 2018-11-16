package com.yunzhou.tdinformation.base.fragment;

import android.os.Bundle;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.fragment
 *  @文件名:   LazyBaseFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */
public abstract class LazyBaseFragment2 extends PermissionFragment {
    private static final String TAG = "LazyBaseFragment2";
    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    /**
     * 懒加载方法
     */
    protected abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
