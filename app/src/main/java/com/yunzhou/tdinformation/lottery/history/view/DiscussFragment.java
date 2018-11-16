package com.yunzhou.tdinformation.lottery.history.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.common.utils.UiUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.lottery.history.DiscussCommentBar;
import com.yunzhou.tdinformation.lottery.history.adapter.DiscussAdapter;
import com.yunzhou.tdinformation.lottery.history.presenter.DiscussPresenter;
import com.yunzhou.tdinformation.user.UserManager;

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

public class DiscussFragment extends LazyBaseFragment2 implements View.OnClickListener, DiscussAdapter.Callback, DiscussView {
    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    @BindView(R.id.fl_bottom_container)
    FrameLayout mFlBottom;

    private int mLotteryId;
    private String mExpect;
    private View mStatusView;
    private View mLoadingView;
    private DiscussPresenter mPresenter;
    private DiscussAdapter mAdapter;
    private DiscussCommentBar mDelegation;

    @Override
    protected void fetchData() {
        mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_discus;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mLotteryId = bundle.getInt(AppConst.Param.LOTTERY_ID);
        mExpect = bundle.getString(AppConst.Extra.LOTTERY_EXPECT);
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
        mPresenter = new DiscussPresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN, mLotteryId, mExpect);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
    }

    private void initRecycleView() {
        mAdapter = new DiscussAdapter(mContext, this);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
    }

    public static DiscussFragment newInstance(int lotteryId, String expect) {
        Bundle args = new Bundle();
        args.putInt(AppConst.Param.LOTTERY_ID, lotteryId);
        args.putString(AppConst.Extra.LOTTERY_EXPECT, expect);
        DiscussFragment fragment = new DiscussFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mLotteryId, mExpect);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCommentLikeClick(GeneralCommentBean commentBean) {
        if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
            LoginActivity.start(mContext);
            return false;
        }
        mPresenter.insertLike(mContext, commentBean);
        return true;
    }

    @Override
    public void showEmptyView(final int visibility) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
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
                initBottomBar(0);
            }
        });
    }

    @Override
    public void showErrorView(final int visibility) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
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
                mStatusView.findViewById(R.id.iv_empty).setOnClickListener(DiscussFragment.this);
                mStatusView.setVisibility(visibility);
            }
        });

    }

    @Override
    public void showLoadMoreData(final List<GeneralCommentBean> realCommentList) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mAdapter.addAll(realCommentList);
            }
        });
    }

    @Override
    public void showDataView(final List<GeneralCommentBean> realCommentList) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(realCommentList);
                initBottomBar(mPresenter.getTotal());
            }
        });
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void showInsertComment(GeneralCommentBean bean) {
        mDelegation.setCommitButtonEnable(true);
        mDelegation.getBottomSheet().dismiss();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void showInsertCommentError() {
        mDelegation.setCommitButtonEnable(true);
        mDelegation.getBottomSheet().dismiss();
        ToastUtil.showShort(mContext, "网络异常~");
    }

    private void initBottomBar(int count) {
        mDelegation = DiscussCommentBar.delegation(mContext, mFlBottom);

        mDelegation.showCommentCount(count);

        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mDelegation.getBottomSheet().getCommentText().replaceAll("[\\s\\n]+", " ").trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShort(mContext, "请输入文字");
                    return;
                }
                if (!UserManager.getInstance().isLogin()) {
                    LoginActivity.start(mContext);
                    return;
                }
                mPresenter.comment(mContext, content, mLotteryId, mExpect);
                mDelegation.setCommitButtonEnable(false);
            }
        });
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
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingView == null) {
                    mLoadingView = mVsLoad.inflate();
                }
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void finishLoadMore(final int delay, final boolean success, final boolean noMoreData) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishLoadMore(delay, success, noMoreData);
            }
        });
    }

    @Override
    public void finishRefresh(final boolean success) {
        UiUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh(success);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
