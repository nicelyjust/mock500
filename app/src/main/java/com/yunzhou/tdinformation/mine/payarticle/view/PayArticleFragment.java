package com.yunzhou.tdinformation.mine.payarticle.view;

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
import com.yunzhou.tdinformation.bean.PayArticleEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.mine.payarticle.PayArticlePresenter;
import com.yunzhou.tdinformation.mine.payarticle.adapter.PayArticleAdapter;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectArticleFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 13:52
 *  @描述：
 */

public class PayArticleFragment extends LazyBaseFragment2 implements PayArticleView,View.OnClickListener{

    private View mLoadingView;

    public static PayArticleFragment newInstance() {
        return new PayArticleFragment();
    }

    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private PayArticlePresenter mPresenter;
    private PayArticleAdapter mAdapter;
    private View mStatusView;

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

    private void createP() {
        mPresenter = new PayArticlePresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadArticle(mContext, AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadArticle(mContext, AppConst.LOAD_TYPE_UP);
            }
        });
    }

    private void initRecycleView() {
        mAdapter = new PayArticleAdapter(mContext);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                PayArticleEntity.ArticlesBean item = mAdapter.getItem(position);
                if (item != null) {
                    WebDetailActivity.start(mContext , 1, String.valueOf(item.getContentId()) , String.valueOf(UserManager.getInstance().getUid()));
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadArticle(mContext, AppConst.LOAD_TYPE_NORMAL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadArticle(mContext, AppConst.LOAD_TYPE_NORMAL);
                break;
            default:

                break;
        }
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
    public void showLoadMoreData(List<PayArticleEntity.ArticlesBean> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void showDataView(List<PayArticleEntity.ArticlesBean> data) {
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
}
