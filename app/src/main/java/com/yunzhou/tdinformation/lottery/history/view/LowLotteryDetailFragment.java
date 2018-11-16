package com.yunzhou.tdinformation.lottery.history.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.LotteryDetailEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.lottery.history.adapter.LowLotteryDetailAdapter;
import com.yunzhou.tdinformation.lottery.history.presenter.LowLotteryDetailPresenter;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history
 *  @文件名:   LowLotteryDetailFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/29 16:49
 *  @描述：
 */

public class LowLotteryDetailFragment extends LazyBaseFragment2 implements LowLotteryDetailView, View.OnClickListener{
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private int mLotteryId;
    private String mExpect;
    private LowLotteryDetailPresenter mPresenter;
    private View mStatusView;
    private View mLoadingView;
    private LowLotteryDetailAdapter mAdapter;
    private View mHeaderView;
    private TextView mSaleNumTv;
    private TextView mBonusTv;
    private TextView mGunCunTv;

    @Override
    protected void fetchData() {
        mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mLotteryId = bundle.getInt(AppConst.Param.LOTTERY_ID);
        mExpect = bundle.getString(AppConst.Extra.LOTTERY_EXPECT);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        createP();
        initPullToRefresh();
        initRecycleView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void createP() {
        mPresenter = new LowLotteryDetailPresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN, mLotteryId, mExpect);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public static LowLotteryDetailFragment newInstance(int lotteryId, String expect) {
        Bundle args = new Bundle();
        args.putInt(AppConst.Param.LOTTERY_ID, lotteryId);
        args.putString(AppConst.Extra.LOTTERY_EXPECT, expect);
        LowLotteryDetailFragment fragment = new LowLotteryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE, true);
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
                break;
            default:
                break;
        }
    }

    @Override
    public void showEmptyView(int visibility, boolean b) {
        if (!b) {
            return;
        }
        if (visibility == View.GONE) {
            if (mStatusView == null) {
                return;
            } else {
                mStatusView.setVisibility(View.GONE);
            }
        }
        if (mStatusView == null) {
            mStatusView = mVsStatus.inflate();
        }
        TextView textView = mStatusView.findViewById(R.id.tv_empty_tip);
        ImageView imageView = mStatusView.findViewById(R.id.iv_empty);
        imageView.setImageResource(R.mipmap.empty);
        textView.setText(R.string.page_empty);
        mStatusView.findViewById(R.id.tv_empty_tip).setOnClickListener(null);
        mStatusView.setVisibility(visibility);
    }

    @Override
    public void showErrorView(int visibility, boolean b) {
        if (!b) {
            return;
        }
        if (visibility == View.GONE) {
            if (mStatusView == null) {
                return;
            } else {
                mStatusView.setVisibility(View.GONE);
            }
        }
        if (mStatusView == null) {
            mStatusView = mVsStatus.inflate();
        }
        TextView textView = mStatusView.findViewById(R.id.tv_empty_tip);
        ImageView imageView = mStatusView.findViewById(R.id.iv_empty);
        imageView.setImageResource(R.mipmap.error);
        textView.setText(R.string.net_error);
        mStatusView.findViewById(R.id.iv_empty).setOnClickListener(this);
        mStatusView.setVisibility(visibility);
    }

    @Override
    public void showDataSuccess(LotteryDetailEntity entities) {
        List<LotteryDetailEntity.BonusSituationDtoListBean> situationDtoList = entities.getBonusSituationDtoList();
        if (mAdapter == null) {
            mAdapter = new LowLotteryDetailAdapter(R.layout.item_lottery_detail_common, situationDtoList);
            if (mHeaderView == null){
                mHeaderView = getLayoutInflater().inflate(R.layout.item_lottery_detail_header, (ViewGroup) mRv.getParent(), false);
                mSaleNumTv = mHeaderView.findViewById(R.id.tv_lottery_sale_number);
                mBonusTv = mHeaderView.findViewById(R.id.tv_lottery_bonus);
                mGunCunTv = mHeaderView.findViewById(R.id.tv_lottery_gun_cun);
            }
            mSaleNumTv.setText(entities.getNationalSales());
            mBonusTv.setText(entities.getCurrentAward());
            mGunCunTv.setText(entities.getRolling());

            mAdapter.setHeaderView(mHeaderView);
            mRv.setAdapter(mAdapter);
        } else {
            if (mHeaderView == null){
                mHeaderView = getLayoutInflater().inflate(R.layout.item_lottery_detail_header, (ViewGroup) mRv.getParent(), false);
                mSaleNumTv = mHeaderView.findViewById(R.id.tv_lottery_sale_number);
                mBonusTv = mHeaderView.findViewById(R.id.tv_lottery_bonus);
                mGunCunTv = mHeaderView.findViewById(R.id.tv_lottery_gun_cun);
            }
            mSaleNumTv.setText(entities.getNationalSales());
            mBonusTv.setText(entities.getCurrentAward());
            mGunCunTv.setText(entities.getRolling());

            mAdapter.setHeaderView(mHeaderView);
            mAdapter.setNewData(situationDtoList);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadingView == null) {
            mLoadingView = mVsLoad.inflate();
        }
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mLoadingView == null) {
            mLoadingView = mVsLoad.inflate();
        }
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
