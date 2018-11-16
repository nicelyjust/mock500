package com.yunzhou.tdinformation.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.base.activity
 *  @文件名:   BaseActivity
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 14:44
 *  @描述：
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    public Context mContext;
    private View mRoot;
    private Unbinder mBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(getArguments());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            mBind = ButterKnife.bind(this, mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }



    protected void onBindViewBefore(View root) {
        // ...
    }



    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }
    /*protected RequestManager getImgLoader(){
        return Glide.with(this);
    }*/

    protected void initWidget(View root) {

    }

    protected void initData() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null && mBind != Unbinder.EMPTY) {
            try {
                mBind.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onRestartInstance(Bundle savedInstanceState) {

    }

    public String getTag1() {
        return this.getClass().getName();
    }
}
