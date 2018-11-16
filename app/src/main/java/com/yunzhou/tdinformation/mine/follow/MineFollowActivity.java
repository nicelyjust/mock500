package com.yunzhou.tdinformation.mine.follow;

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
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.FollowEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.follow
 *  @文件名:   MineFollowActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 9:24
 *  @描述：
 */

public class MineFollowActivity extends BaseActivity<MineFollowPresenter> implements MineFollowAdapter.Callback, MineFollowView, View.OnClickListener {
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
    private MineFollowPresenter mPresenter;
    private MineFollowAdapter mAdapter;
    // 当前用户访问ID
    private int mUid;

    @Override
    protected int getContentView() {
        return R.layout.activity_common_refresh_list;
    }

    @Override
    protected MineFollowPresenter createP(Context context) {
        mPresenter = new MineFollowPresenter();
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
        mUid = getIntent().getIntExtra(AppConst.Extra.USER_ID, 0);
        mTvTitle.setText(mUid == UserManager.getInstance().getUid() ? R.string.follow : R.string.ta_follow);
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRecyclerView() {
        mAdapter = new MineFollowAdapter(this, this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(MineFollowActivity.this, AppConst.LOAD_TYPE_UP, mUid);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(MineFollowActivity.this, AppConst.LOAD_TYPE_DOWN, mUid);
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
        mPresenter.loadData(this, AppConst.LOAD_TYPE_NORMAL, mUid);
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
                mPresenter.loadData(this, AppConst.LOAD_TYPE_NORMAL, mUid);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoadMoreData(List<FollowEntity.FollowBean> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showDataView(List<FollowEntity.FollowBean> data) {
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
    public void showFollowSuccess( String msg, int pos) {
        FollowEntity.FollowBean item = mAdapter.getItem(pos);
        if (item != null){
            if (UserManager.getInstance().isMine(item.getUid())) {
                int status = item.getStatus();
                item.setStatus(status == 1 ? 2 : 1);
            } else{
                int status = item.getMyAttentionStatus();
                item.setMyAttentionStatus(status == 1 ? 2 : 1);
            }
            mAdapter.notifyItemChanged(pos ,1);

        }
    }

    @Override
    public void showFollowError(String message) {
        ToastUtil.showShort(this ,"操作失败");
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
    public void onFollowClick(int pos, int followedUserUid, int status) {
        mPresenter.requestFollow(this, status == 1, followedUserUid, pos);
    }

    public static void start(Context context,int uid) {
        Intent starter = new Intent(context, MineFollowActivity.class);
        starter.putExtra(AppConst.Extra.USER_ID, uid);
        context.startActivity(starter);
    }
}
