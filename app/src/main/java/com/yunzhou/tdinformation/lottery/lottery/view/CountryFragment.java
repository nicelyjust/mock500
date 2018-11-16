package com.yunzhou.tdinformation.lottery.lottery.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.LotteryHistoryActivity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.CountryAdapter;
import com.yunzhou.tdinformation.lottery.history.LowLotteryDetailActivity;
import com.yunzhou.tdinformation.lottery.lottery.presenter.CountryPresenter;
import com.yunzhou.tdinformation.web.WebDetailActivity;

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

public class CountryFragment extends LazyBaseFragment2 implements CountryView, View.OnClickListener, CountryAdapter.Callback {
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    private CountryPresenter mPresenter;
    private View mStatusView;
    private CountryAdapter mAdapter;

    public static CountryFragment newInstance() {
        CountryFragment fragment = new CountryFragment();
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

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadCountryLottery(AppConst.LOAD_TYPE_NORMAL);
        }
    }

    private void createP() {
        mPresenter = new CountryPresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadCountryLottery(AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mAdapter = new CountryAdapter(mContext, this);
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                LotteryEntity item = mAdapter.getItem(position);
                if (item != null) {
                    LowLotteryDetailActivity.start(mContext ,item.getLotteryName() ,item.getId() ,item.getLotteryResultDto());
                }
            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
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
    public void showLotteryData(List<LotteryEntity> lotteryEntities) {
        mAdapter.setData(lotteryEntities);
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
            TextView textView = mStatusView.findViewById(R.id.tv_empty_tip);
            ImageView imageView = mStatusView.findViewById(R.id.iv_empty);
            imageView.setImageResource(R.mipmap.error);
            textView.setText(R.string.net_error);
            mStatusView.findViewById(R.id.iv_empty).setOnClickListener(this);
        }
        mStatusView.setVisibility(visibility);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadCountryLottery(AppConst.LOAD_TYPE_NORMAL);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFuncClick(String lotteryName, LotteryEntity.LotteryFunctionListBean item) {
        switch (item.getFunctionTag()) {
            case 1:
                LotteryHistoryActivity.start(mContext, item.getLotteryId(), lotteryName);
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
                ToastUtil.showShort(mContext, item.getFunctionName());
                break;
        }
    }

    @Override
    public void onItemClick(int pos) {
        LotteryEntity item = mAdapter.getItem(pos);
        if (item != null) {
            LowLotteryDetailActivity.start(mContext ,item.getLotteryName() ,item.getId() ,item.getLotteryResultDto());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
