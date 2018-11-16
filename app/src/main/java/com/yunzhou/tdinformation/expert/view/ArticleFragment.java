package com.yunzhou.tdinformation.expert.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.expert.ArticleEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.expert.adapter.ArticleAdapter;
import com.yunzhou.tdinformation.expert.presenter.ArticlePresenter;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ArticleFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 18:48
 *  @描述：
 */

public class ArticleFragment extends LazyBaseFragment2 implements ArticleView, View.OnClickListener {

    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    private int mExpertId;
    private ArticlePresenter mPresenter;
    private ArticleAdapter mAdapter;
    private View mStatusView;
    private String mHeadUrl;

    public static ArticleFragment newInstance(int expertId, String headUrl) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConst.Extra.EXPERT_ID, expertId);
        bundle.putString(AppConst.Extra.EXPERT_HEAD_URL, headUrl);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
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
        mPresenter = new ArticlePresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadArticle(AppConst.LOAD_TYPE_DOWN, mExpertId);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.onLoadMoreArticle(AppConst.LOAD_TYPE_UP, mExpertId);
            }
        });
    }

    private void initRecycleView() {
        mAdapter = new ArticleAdapter(mContext ,mHeadUrl);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                ArticleEntity.ArticleBean item = mAdapter.getItem(position);
                if (item != null) {
                    WebDetailActivity.start(mContext , 1, String.valueOf(item.getId()) , String.valueOf(UserManager.getInstance().getUid()));
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadArticle(AppConst.LOAD_TYPE_NORMAL, mExpertId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mExpertId = bundle.getInt(AppConst.Extra.EXPERT_ID);
        mHeadUrl = bundle.getString(AppConst.Extra.EXPERT_HEAD_URL);
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
    public void showLoadMoreData(List<ArticleEntity.ArticleBean> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void showDataView(List<ArticleEntity.ArticleBean> data) {
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadArticle(AppConst.LOAD_TYPE_NORMAL, mExpertId);
                break;
            default:

                break;
        }
    }
}
