package com.yunzhou.tdinformation.mine.campaign;

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
import com.yunzhou.tdinformation.bean.ActivityEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine
 *  @文件名:   CampaignActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 12:53
 *  @描述：
 */

public class CampaignActivity extends BaseActivity<CampaignPresenter> implements CampaignView, View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_blog_list)
    RecyclerView mRv;
    @BindView(R.id.srl_blog_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_empty_blog_list)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load_list)
    ViewStub mVsLoad;
    private View mStatusView;
    private View mLoadingView;
    private CampaignPresenter mPresenter;
    private CampaignAdapter mAdapter;
    private Map<String, String> mParams;

    @Override
    protected int getContentView() {
        return R.layout.activity_common_refresh_list;
    }

    @Override
    protected CampaignPresenter createP(Context context) {
        mPresenter = new CampaignPresenter();
        return mPresenter;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText(R.string.campaign_center);
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRecyclerView() {
        mAdapter = new CampaignAdapter(this);
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                ActivityEntity.ActivityBean item = mAdapter.getItem(position);

                if (item != null) {
                    if (mParams == null) {
                        mParams = new HashMap<>();
                    }
                    mParams.put("uid", String.valueOf(UserManager.getInstance().getUid()));
                    mParams.put("activityId", String.valueOf(item.getId()));
                    WebDetailActivity.start(CampaignActivity.this, 2, Utils.addBasicParams(item.getCarouselUrl(), mParams));
                }
            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(AppConst.LOAD_TYPE_UP);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL);
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
                showErrorView(View.GONE);
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoadMoreData(List<ActivityEntity.ActivityBean> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showDataView(List<ActivityEntity.ActivityBean> data) {
        mAdapter.setData(data);
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

    public static void start(Context context) {
        Intent starter = new Intent(context, CampaignActivity.class);
        context.startActivity(starter);
    }
}
