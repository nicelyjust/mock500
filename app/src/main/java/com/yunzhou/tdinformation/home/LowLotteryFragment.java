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
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.adapter.LowLotteryAdapter;
import com.yunzhou.tdinformation.home.presenter.LowLotteryPresenter;
import com.yunzhou.tdinformation.home.view.LowLotteryView;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home
 *  @文件名:   LowLotteryFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 18:40
 *  @描述：
 */

public class LowLotteryFragment extends LazyBaseFragment2 implements LowLotteryView, LowLotteryAdapter.Callback {


    @BindView(R.id.rv_head_line)
    RecyclerView mRv;
    @BindView(R.id.srl_home_line)
    SmartRefreshLayout mRefreshLayout;
    private LowLotteryPresenter mPresenter;
    private LowLotteryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_head_line;
    }
    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadChannelColumn();
            mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL);
        }
    }
    public static LowLotteryFragment newInstance() {

        Bundle args = new Bundle();

        LowLotteryFragment fragment = new LowLotteryFragment();
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
        mAdapter = new LowLotteryAdapter(mContext ,this);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadChannelColumn();
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(AppConst.LOAD_TYPE_UP);
            }
        });
    }

    private void createP() {
        mPresenter = new LowLotteryPresenter();
   }
    @Override
    public void getLotteryResult(int pos, int channelId) {
        mPresenter.loadChannelLottery(pos ,channelId);
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
    public void showLotteryColumn(List<LotteryChannelEntity> entities) {
        mAdapter.setLotteryColumnData(entities);
    }

    @Override
    public void loadLotteryView(int pos, LotteryEntity.LotteryResultDtoBean entity) {
        mAdapter.setLotteryData(pos ,entity);
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
