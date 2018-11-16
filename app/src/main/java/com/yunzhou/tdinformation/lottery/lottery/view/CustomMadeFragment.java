package com.yunzhou.tdinformation.lottery.lottery.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.history.LowLotteryDetailActivity;
import com.yunzhou.tdinformation.lottery.lottery.CustomLotteryDialogFragment;
import com.yunzhou.tdinformation.lottery.lottery.LotteryHistoryActivity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.CustomMadeAdapter;
import com.yunzhou.tdinformation.lottery.lottery.presenter.CustomMadePresenter;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.view
 *  @文件名:   CountryFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 15:13
 *  @描述：
 */

public class CustomMadeFragment extends LazyBaseFragment2 implements CustomMadeView, View.OnClickListener, CustomMadeAdapter.Callback {
    private static final String TAG = "CustomMadeFragment";
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private View mStatusView;
    private View mLoadingView;
    private CustomMadePresenter mPresenter;
    private CustomMadeAdapter mAdapter;
    private RefreshBroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_conmon_refresh_color_list;
    }

    public static CustomMadeFragment newInstance() {

        CustomMadeFragment fragment = new CustomMadeFragment();
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        createP();
        initPullToRefresh();
        initRecycleView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void createP() {
        mPresenter = new CustomMadePresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadCustomLottery(mContext, AppConst.LOAD_TYPE_DOWN);
                mPresenter.loadRecommendLottery(AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mAdapter = new CustomMadeAdapter(mContext, this);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        showLoading();
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadCustomLottery(mContext, AppConst.LOAD_TYPE_NORMAL);
            mPresenter.loadRecommendLottery(AppConst.LOAD_TYPE_NORMAL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                showLoading();
                mPresenter.loadCustomLottery(mContext, AppConst.LOAD_TYPE_NORMAL);
                mPresenter.loadRecommendLottery(AppConst.LOAD_TYPE_NORMAL);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mReceiver == null) {
            mFilter = new IntentFilter("com.yunzhou.action.refresh.custom");
            mReceiver = new RefreshBroadcastReceiver(this);
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver,mFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onSubscribeClick(RecommendLotteryEntity item) {
        mPresenter.subscribeLottery(mContext ,item.getId());
    }

    @Override
    public void onSubscribeMoreClick() {
        CustomLotteryDialogFragment dialogFragment = new CustomLotteryDialogFragment();
        dialogFragment.show(getChildFragmentManager() ,"custom");
    }

    @Override
    public void onFuncClick(String lotteryName, LotteryEntity.LotteryFunctionListBean item) {
        switch (item.getFunctionTag()) {
            case 1:
                LotteryHistoryActivity.start(mContext , item.getLotteryId(), lotteryName);
                break;
            case 2:
                String chartUrl = item.getChartUrl();
                if (chartUrl.contains("http://")) {
                    WebDetailActivity.start(mContext, 2, chartUrl);
                } else {
                    WebDetailActivity.start(mContext, 2, NetConstant.BASE_URL.concat(chartUrl));
                }
                break;
            default:
                ToastUtil.showShort(mContext ,item.getFunctionName());
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        LotteryEntity item = mAdapter.getSubscribeItem(position);
        if (item != null) {
            if ("HIGH_RATE".equals(item.getLotteryType())) {
                // TODO: 2018/10/29 进入高频彩开奖详情
            } else {
                LowLotteryDetailActivity.start(mContext ,item.getLotteryName() ,item.getId() ,item.getLotteryResultDto());
            }
        }
    }

    @Override
    public void showCustomLotteryEmpty(boolean needChange) {
        if (needChange) {
            mAdapter.clearSubscribeData();
        }
        Log.e(TAG, "showCustomLotteryEmpty: "+ needChange);
    }

    @Override
    public void showRecommendLotteryEmpty(boolean needChange) {
        if (needChange) {
            mAdapter.clearRecommendData();
        }
        Log.e(TAG, "showRecommendLotteryEmpty: "+needChange );
    }

    @Override
    public void showRecommendLottery(List<RecommendLotteryEntity> entities) {
        mAdapter.setRecommendData(entities);
    }

    @Override
    public void showCustomLottery(List<SubscribeLotteryEntity> entities) {
        mAdapter.setSubscribeData(entities);
    }

    @Override
    public void showSubscribeLotteryError(String message) {
        ToastUtil.showShort(mContext ,message);
    }

    @Override
    public void showSubscribeLotterySuccess() {
        mPresenter.loadCustomLottery(mContext, AppConst.LOAD_TYPE_DOWN);
        mPresenter.loadRecommendLottery(AppConst.LOAD_TYPE_DOWN);
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
    public void showEmptyView(int visibility) {
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
    public void showErrorView(int visibility) {
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    static class RefreshBroadcastReceiver extends BroadcastReceiver{

        private final WeakReference<CustomMadeFragment> mWeakReference;

        public RefreshBroadcastReceiver(CustomMadeFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            CustomMadeFragment customMadeFragment = mWeakReference.get();
            if (customMadeFragment != null) {
                if (customMadeFragment.mRefreshLayout.autoRefresh()){
                    customMadeFragment.mRefreshLayout.setNoMoreData(false);
                    customMadeFragment.mPresenter.loadCustomLottery(customMadeFragment.mContext, AppConst.LOAD_TYPE_DOWN);
                    customMadeFragment.mPresenter.loadRecommendLottery(AppConst.LOAD_TYPE_DOWN);
                }
            }
        }
    }
}
