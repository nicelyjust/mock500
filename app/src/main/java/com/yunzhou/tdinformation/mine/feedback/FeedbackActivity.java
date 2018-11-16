package com.yunzhou.tdinformation.mine.feedback;

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
import com.yunzhou.tdinformation.bean.HelpEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.mine.help.HelpAdapter;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.feedback
 *  @文件名:   FeedbackActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 18:58
 *  @描述：
 */

public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements HelpAdapter.OnItemClickListener,View.OnClickListener,FeedbackView {
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

    private FeedbackPresenter mPresenter;
    private HelpAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_common_refresh_list;
    }

    @Override
    protected FeedbackPresenter createP(Context context) {
        mPresenter = new FeedbackPresenter();
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
        mTvTitle.setText(R.string.feedback_center);
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRecyclerView() {
        mAdapter = new HelpAdapter(this , this ,1);
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                HelpEntity item = mAdapter.getItem(position);

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
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
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
    public void onItemClick(int pos) {

    }
    @Override
    public void showLoadMoreData(List<HelpEntity> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showDataView(List<HelpEntity> data) {
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
        Intent starter = new Intent(context, FeedbackActivity.class);
        context.startActivity(starter);
    }
}
