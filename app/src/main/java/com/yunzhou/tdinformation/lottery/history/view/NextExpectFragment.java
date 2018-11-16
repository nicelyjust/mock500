package com.yunzhou.tdinformation.lottery.history.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.ExpectPredictEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.lottery.history.adapter.NextPredictAdapter;
import com.yunzhou.tdinformation.lottery.history.presenter.NextExpectPresenter;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.web.WebDetailActivity;

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

public class NextExpectFragment extends LazyBaseFragment2 implements NextExpectView, View.OnClickListener {
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private NextExpectPresenter mPresenter;
    private int mLotteryId;
    private String mExpect;
    private View mLoadingView;
    private View mStatusView;
    private NextPredictAdapter mAdapter;

    @Override
    protected void fetchData() {
        mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
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
    protected void initBundle(Bundle bundle) {
        mLotteryId = bundle.getInt(AppConst.Param.LOTTERY_ID);
        mExpect = bundle.getString(AppConst.Extra.LOTTERY_EXPECT);
    }

    private void createP() {
        mPresenter = new NextExpectPresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN, mLotteryId, mExpect);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(AppConst.LOAD_TYPE_UP, mLotteryId, mExpect);
            }
        });
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);

    }

    private void initRecycleView() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new NextPredictAdapter(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ExpectPredictEntity item = (ExpectPredictEntity) adapter.getItem(position);
                if (item != null) {
                    WebDetailActivity.start( mContext,1 ,String.valueOf(item.getContentId()) ,String.valueOf(UserManager.getInstance().getUid()));
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    public static NextExpectFragment newInstance(int lotteryId, String expect) {
        Bundle args = new Bundle();
        args.putInt(AppConst.Param.LOTTERY_ID, lotteryId);
        args.putString(AppConst.Extra.LOTTERY_EXPECT, expect);
        NextExpectFragment fragment = new NextExpectFragment();
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
    public void showLoadMoreData(List<ExpectPredictEntity> entities) {
        mAdapter.addData(entities);
    }

    @Override
    public void showDataView(List<ExpectPredictEntity> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                showLoading();
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
                break;
            default:

                break;
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
