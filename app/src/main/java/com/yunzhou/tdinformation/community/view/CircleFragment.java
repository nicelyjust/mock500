package com.yunzhou.tdinformation.community.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.community.CircleItemEntity;
import com.yunzhou.tdinformation.blog.BlogListActivity;
import com.yunzhou.tdinformation.community.adapter.CircleAdapter;
import com.yunzhou.tdinformation.community.presenter.CirclePresenter;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community
 *  @文件名:   CircleFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 11:24
 *  @描述：    圈子
 */

public class CircleFragment extends LazyBaseFragment2 implements CircleView, CircleAdapter.Callback, View.OnClickListener {
    private static final String TAG = "CircleFragment";
    @BindView(R.id.rv_circle)
    RecyclerView mRv;
    @BindView(R.id.srl_circle)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_empty_circle)
    ViewStub mVsEmpty;
    private CirclePresenter mPresenter;
    private CircleAdapter mAdapter;
    private View mStatusView;

    public static CircleFragment newInstance() {
        CircleFragment fragment = new CircleFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
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
        mPresenter = new CirclePresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableOverScrollBounce(true);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mAdapter = new CircleAdapter(mContext , this);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setItemAnimator(null);
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        mPresenter.loadCircleData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            mPresenter.loadCircleData();
    }

    @Override
    public void showCircleData(List<CircleItemEntity> circleItemEntities) {
        mAdapter.setData(circleItemEntities);
    }

    @Override
    public void showCircleDataError(int visibility, String msg) {
        if (TextUtils.isEmpty(msg))
            Log.d(TAG, "showCircleDataError: " + msg);
        if (visibility == View.GONE) {
            if (mStatusView == null) {
                return;
            }else{
                mStatusView.setVisibility(View.GONE);
            }
        }
        if (mStatusView == null) {
            mStatusView = mVsEmpty.inflate();
            TextView textView = mStatusView.findViewById(R.id.tv_empty_tip);
            ImageView imageView = mStatusView.findViewById(R.id.iv_empty);
            imageView.setImageResource(R.mipmap.error);
            textView.setText(R.string.net_error);
            mStatusView.findViewById(R.id.iv_empty).setOnClickListener(this);
            mStatusView.setVisibility(visibility);
        }

    }

    @Override
    public void onItemClick(int pos) {
        CircleItemEntity item = mAdapter.getItem(pos);
        BlogListActivity.start(mContext ,String.valueOf(item.getCircleType()),item.getCircleTitle());
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
                showCircleDataError(View.GONE ,"");
                mPresenter.loadCircleData();
                break;
            default:
                break;
        }
    }
}
