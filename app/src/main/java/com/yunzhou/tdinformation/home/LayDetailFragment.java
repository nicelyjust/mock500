package com.yunzhou.tdinformation.home;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   LayDetailFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 18:42
 *  @描述：    竞彩详情界面
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.adapter.LayDetailAdapter;
import com.yunzhou.tdinformation.home.presenter.LayDetailPresenter;
import com.yunzhou.tdinformation.home.view.LayDetailView;

import java.util.List;

import butterknife.BindView;

public class LayDetailFragment extends LazyBaseFragment2 implements LayDetailView{
    @BindView(R.id.rv_head_line)
    RecyclerView mRv;
    @BindView(R.id.srl_home_line)
    SmartRefreshLayout mRefreshLayout;
    private LayDetailPresenter mPresenter;
    private LayDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_head_line;
    }

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()){
            mPresenter.loadDataInfo(AppConst.LOAD_TYPE_NORMAL);
        }
    }

    public static LayDetailFragment newInstance() {

        Bundle args = new Bundle();

        LayDetailFragment fragment = new LayDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        createP();
        initPullToRefresh();
        initRecyclerView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initRecyclerView() {
        mAdapter = new LayDetailAdapter(mContext);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadDataInfo(AppConst.LOAD_TYPE_DOWN);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadDataInfo(AppConst.LOAD_TYPE_UP);
            }
        });
    }

    private void createP() {
        mPresenter = new LayDetailPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay,success,noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    @Override
    public void showLoadMoreData(List<ContentEntity> contents) {
        mAdapter.addAll(contents);
    }

    @Override
    public void showDataView(List<ContentEntity> contents) {
        mAdapter.setData(contents);
    }
}
