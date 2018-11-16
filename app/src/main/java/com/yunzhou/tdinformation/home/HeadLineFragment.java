package com.yunzhou.tdinformation.home;

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
import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.adapter.HeadLineAdapter;
import com.yunzhou.tdinformation.home.holder.HeaderViewHolder;
import com.yunzhou.tdinformation.home.presenter.HeadLinePresenter;
import com.yunzhou.tdinformation.home.view.HeadLineView;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   HeadLineFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 18:38
 *  @描述：
 */

public class HeadLineFragment extends LazyBaseFragment2 implements HeadLineView {
    private static final String TAG = "HeadLineFragment";
    @BindView(R.id.rv_head_line)
    RecyclerView mRv;
    @BindView(R.id.srl_home_line)
    SmartRefreshLayout mRefreshLayout;
    private HeadLinePresenter mPresenter;
    private HeadLineAdapter mAdapter;

    public static HeadLineFragment newInstance() {

        Bundle args = new Bundle();

        HeadLineFragment fragment = new HeadLineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_head_line;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mRv != null){
                RecyclerView.ViewHolder viewHolder = mRv.findViewHolderForAdapterPosition(0);
                if (viewHolder != null && viewHolder instanceof HeaderViewHolder) {
                    HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                    holder.mBanner.start();
                    holder.mMarqueeView.startFlipping();

                }
            }
        } else {
            if (mRv != null){
                RecyclerView.ViewHolder viewHolder = mRv.findViewHolderForAdapterPosition(0);
                if (viewHolder != null && viewHolder instanceof HeaderViewHolder) {
                    HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                    holder.mBanner.releaseBanner();
                    holder.mMarqueeView.stopFlipping();
                }
            }
        }
    }

    @Override
    protected void fetchData() {
        mPresenter.loadBanner();
        mPresenter.loadExperts();
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadHeadline(AppConst.LOAD_TYPE_NORMAL);
        }
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
        mAdapter = new HeadLineAdapter(mContext);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.onRefresh();

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.onLoadMore();
            }
        });
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);
    }

    private void createP() {
        mPresenter = new HeadLinePresenter();
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
    public void showBannerView(BannerEntity bannerEntity) {
        mAdapter.setBannerData(bannerEntity);
    }

    @Override
    public void showLoadMoreHeadLineData(List<ContentEntity> contents) {
        mAdapter.addAll(contents);
    }

    @Override
    public void showHeadLineDataView(List<ContentEntity> contents) {
        mAdapter.setData(contents);
    }

    @Override
    public void showExpertView(List<ExpertEntity> expertEntities) {
        mAdapter.setExpertData(expertEntities);
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay,success,noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }
}
