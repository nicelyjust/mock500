package com.yunzhou.tdinformation.lottery.lottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.lottery.history.LowLotteryDetailActivity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.LowLotteryHistoryAdapter;
import com.yunzhou.tdinformation.lottery.lottery.presenter.LotteryHistoryPresenter;
import com.yunzhou.tdinformation.lottery.lottery.view.LotteryHistoryView;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery
 *  @文件名:   LotteryHistoryActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 11:24
 *  @描述：全国,地方都是低频彩; 低频彩的开奖历史界面
 */

public class LotteryHistoryActivity extends BaseActivity<LotteryHistoryPresenter> implements LotteryHistoryView, View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_list)
    RecyclerView mRv;
    @BindView(R.id.srl_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_empty_list)
    ViewStub mVsEmpty;
    @BindView(R.id.vs_load_list)
    ViewStub mVsLoad;
    private LotteryHistoryPresenter mPresenter;
    private View mStatusView;
    private View mLoadingView;
    private int mLotteryId;
    private LowLotteryHistoryAdapter mAdapter;
    private String mLotteryName;

    @Override
    protected int getContentView() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("更多期号");
        mLotteryId = getIntent().getIntExtra(AppConst.Param.LOTTERY_ID, -1);
        mLotteryName = getIntent().getStringExtra(AppConst.Extra.LOTTERY_NAME);
        initRefreshLayout();
        initRecyclerView();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN, mLotteryId);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(AppConst.LOAD_TYPE_UP, mLotteryId);
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new LowLotteryHistoryAdapter(this);
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                LotteryEntity.LotteryResultDtoBean item = mAdapter.getItem(position);
                if (item != null) {
                    LowLotteryDetailActivity.start(LotteryHistoryActivity.this ,mLotteryName ,mLotteryId ,item);
                }
            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected LotteryHistoryPresenter createP(Context context) {
        mPresenter = new LotteryHistoryPresenter();
        return mPresenter;
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId);
        }
    }

    @Override
    public void showLoadMoreData(List<LotteryEntity.LotteryResultDtoBean> lotteryResults) {
        mAdapter.addAll(lotteryResults);
    }

    @Override
    public void showDataView(List<LotteryEntity.LotteryResultDtoBean> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                showLoading();
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId);
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
            mStatusView = mVsEmpty.inflate();
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
            mStatusView = mVsEmpty.inflate();
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

    public static void start(Context context, int lotteryId, String lotteryName) {
        Intent starter = new Intent(context, LotteryHistoryActivity.class);
        starter.putExtra(AppConst.Param.LOTTERY_ID, lotteryId);
        starter.putExtra(AppConst.Extra.LOTTERY_NAME, lotteryName);
        context.startActivity(starter);
    }
}
