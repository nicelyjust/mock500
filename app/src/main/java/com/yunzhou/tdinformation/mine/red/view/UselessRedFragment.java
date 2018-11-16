package com.yunzhou.tdinformation.mine.red.view;

import android.content.Context;
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
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.mine.red.IRedListener;
import com.yunzhou.tdinformation.mine.red.adapter.UselessRedAdapter;
import com.yunzhou.tdinformation.mine.red.presenter.MyRedPacketPresenter;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   UseRedFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/13 17:03
 *  @描述：
 */

public class UselessRedFragment extends LazyBaseFragment2 implements MyRedPacketView, View.OnClickListener{
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private MyRedPacketPresenter mPresenter;
    private UselessRedAdapter mAdapter;
    private View mStatusView;
    private View mLoadingView;
    private IRedListener mIRedListener;

    public static UselessRedFragment newInstance() {
        return new UselessRedFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIRedListener = (IRedListener) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement IRedListener");
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    @Override
    protected void initWidget(View root) {
        createP();
        initRecyclerView();
        initRefreshLayout();
        mPresenter.attachView(this);
    }

    private void createP() {
        mPresenter = new MyRedPacketPresenter();
    }

    private void initRecyclerView() {
        mAdapter = new UselessRedAdapter(mContext);
        mRv.setBackgroundColor(getResources().getColor(R.color.base_F5F5F5));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadUselessData(mContext, AppConst.LOAD_TYPE_UP);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadUselessData(mContext, AppConst.LOAD_TYPE_DOWN);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }
    @Override
    protected void fetchData() {
        mPresenter.loadUselessData(mContext, AppConst.LOAD_TYPE_NORMAL);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadUselessData(mContext, AppConst.LOAD_TYPE_NORMAL);
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
    public void showDataView(List<RedPacksBean> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showLoadMoreData(List<RedPacksBean> list) {
        // mAdapter.addAll(list);
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
