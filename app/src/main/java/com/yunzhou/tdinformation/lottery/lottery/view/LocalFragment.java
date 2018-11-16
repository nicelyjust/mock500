package com.yunzhou.tdinformation.lottery.lottery.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryProvinceEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.history.LowLotteryDetailActivity;
import com.yunzhou.tdinformation.lottery.lottery.LotteryHistoryActivity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.CountryAdapter;
import com.yunzhou.tdinformation.lottery.lottery.adapter.ProvinceItemAdapter;
import com.yunzhou.tdinformation.lottery.lottery.presenter.LocalPresenter;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.ArrayList;
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

public class LocalFragment extends LazyBaseFragment2 implements LocalView, View.OnClickListener, CountryAdapter.Callback {
    private static final String TAG = "LocalFragment";
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private LocalPresenter mPresenter;
    private View mStatusView;
    private View mLoadingView;
    private ProvinceItemAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    public static LocalFragment newInstance() {

        LocalFragment fragment = new LocalFragment();
        return fragment;
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
        mPresenter = new LocalPresenter(LocalPresenter.FROM_LOCAL);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadLottery(AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadLottery(AppConst.LOAD_TYPE_UP);
            }
        });
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void fetchData() {
        showLoading();
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadLottery(AppConst.LOAD_TYPE_DOWN);
        }
    }
    @Override
    public void showLocalLottery(List<LotteryProvinceEntity> provinceEntity) {
    }

    @Override
    public void showLoadMoreLocalLottery(ArrayList<MultiItemEntity> provinceEntity) {
        if (mAdapter != null) {
            mAdapter.addData(provinceEntity);
        }
    }

    @Override
    public void showLocalLottery(ArrayList<MultiItemEntity> realEntity) {
        if (mAdapter == null) {
            mAdapter = new ProvinceItemAdapter(realEntity ,this);
            mRv.setAdapter(mAdapter);
        } else {
            mAdapter.setNewData(realEntity);
        }
        mAdapter.expand(0, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadLottery(AppConst.LOAD_TYPE_NORMAL);
                break;
            default:

                break;
        }
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
    public void onItemClick(int pos) {
        LotteryEntity item = (LotteryEntity) mAdapter.getItem(pos);
        if (item != null) {
            LowLotteryDetailActivity.start(mContext ,item.getLotteryName() ,item.getId() ,item.getLotteryResultDto());
        }
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
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
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
}
