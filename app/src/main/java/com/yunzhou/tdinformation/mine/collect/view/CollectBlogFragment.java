package com.yunzhou.tdinformation.mine.collect.view;

import android.net.Uri;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.blog.BlogDetailActivity;
import com.yunzhou.tdinformation.community.adapter.BlogAdapter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.mine.collect.presenter.CollectBlogPresenter;
import com.yunzhou.tdinformation.profile.ProfileActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;

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

public class CollectBlogFragment extends LazyBaseFragment2 implements CollectBlogView ,View.OnClickListener, MessagePicturesLayout.Callback, BlogAdapter.OnBlogClickListener {

    @BindView(R.id.rv_common_list)
    RecyclerView mRv;
    @BindView(R.id.srl_common_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    CollectBlogPresenter mPresenter;
    private View mLoadingView;
    private View mStatusView;
    private BlogAdapter mAdapter;

    public static CollectBlogFragment newInstance() {
        return new CollectBlogFragment();
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

    private void createP() {
        mPresenter = new CollectBlogPresenter();
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(mContext, AppConst.LOAD_TYPE_DOWN);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(mContext, AppConst.LOAD_TYPE_UP);
            }
        });
    }

    private void initRecycleView() {
        mAdapter = new BlogAdapter(mContext ,BlogAdapter.TYPE_OTHER).setPictureClickCallback(this);
        mAdapter.setOnBlogClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

        mRv.setLayoutManager(linearLayoutManager);
        mRv.addItemDecoration(new DividerItemDecoration(mContext , LinearLayout.VERTICAL));
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadData(mContext, AppConst.LOAD_TYPE_NORMAL);
        }
    }
    @Override
    public void onJumpToProfile(int uid) {
        ProfileActivity.start(mContext ,uid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadData(mContext, AppConst.LOAD_TYPE_NORMAL);
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
    public void showLoadMoreBlogData(List<Blog> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showBlogDataView(List<Blog> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showCollectSuccess(int preIsCollect) {
        ToastUtil.showShort(mContext, preIsCollect == 0 ? "收藏成功" : "取消收藏成功");
    }

    @Override
    public void showCollectError(int postId) {
        // TODO: 2018/11/3  优化用户体验
    }

    @Override
    public void showLikeSuccess(int preIsLike) {
        ToastUtil.showShort(mContext, preIsLike == 0 ? "点赞成功" : "取消点赞成功");
    }

    @Override
    public void showLikeError(int postId) {
        // TODO: 2018/11/3  优化用户体验
    }

    @Override
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {

    }

    @Override
    public boolean onCollectClick(int preIsCollect, int pos) {
        if (!UserManager.getInstance().isLogin()) {
            LoginActivity.start(mContext);
            return false;
        } else {
            Blog item = mAdapter.getItem(pos);
            if (item != null) {
                mPresenter.addCollect(mContext, item.getPostId(), preIsCollect);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onLikeClick(int preIsLike, int pos) {
        if (!UserManager.getInstance().isLogin()) {
            LoginActivity.start(mContext);
            return false;
        } else {
            Blog item = mAdapter.getItem(pos);
            if (item != null) {
                mPresenter.insertLike(mContext, item.getPostId() ,preIsLike);
                return true;
            }
            return false;
        }
    }

    @Override
    public void onCommentClick(int pos) {
        Blog blog = mAdapter.getItem(pos);
        if (blog != null){
            BlogDetailActivity.start(mContext ,blog.getPostId());
        }
    }

    @Override
    public void onDeleteClick(int pos) {
    }

    @Override
    public void showEmptyData() {
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
