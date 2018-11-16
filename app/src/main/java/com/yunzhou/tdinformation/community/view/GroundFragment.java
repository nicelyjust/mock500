package com.yunzhou.tdinformation.community.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.blog.BlogDetailActivity;
import com.yunzhou.tdinformation.blog.PostBlogActivity;
import com.yunzhou.tdinformation.community.adapter.BlogAdapter;
import com.yunzhou.tdinformation.community.presenter.GroundPresenter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.profile.ProfileActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community
 *  @文件名:   GroundFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 11:25
 *  @描述：    广场
 */

public class GroundFragment extends LazyBaseFragment2 implements GroundView, View.OnClickListener, MessagePicturesLayout.Callback, BlogAdapter.OnBlogClickListener {
    private static final String TAG = "GroundFragment";
    @BindView(R.id.rv_community)
    RecyclerView mRv;
    @BindView(R.id.srl_community)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fab_post)
    FloatingActionButton mFabPost;
    private GroundPresenter mPresenter;
    private BlogAdapter mAdapter;
    private ImageWatcherHelper iwHelper;

    public static GroundFragment newInstance() {
        GroundFragment fragment = new GroundFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ground;
    }

    @Override
    protected void initWidget(View root) {
        createP();
        initPullToRefresh();
        initRecyclerView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mFabPost.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof ImageWatcherHelper.Provider) {
            iwHelper = ((ImageWatcherHelper.Provider) getActivity()).iwHelper();
        }
    }

    @Override
    protected void fetchData() {
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadAdminPost(UserManager.getInstance().getUid());
            mPresenter.loadDataInfo(AppConst.LOAD_TYPE_NORMAL, UserManager.getInstance().getUid());
        }
    }

    private void initRecyclerView() {
        mAdapter = new BlogAdapter(mContext ,BlogAdapter.TYPE_GROUND).setPictureClickCallback(this);
        mAdapter.setOnBlogClickListener(this);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setAdapter(mAdapter);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(mContext).resumeRequests();
                } else {
                    Glide.with(mContext).pauseRequests();
                }
            }
        });

    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadAdminPost(UserManager.getInstance().getUid());
                mPresenter.loadDataInfo(AppConst.LOAD_TYPE_DOWN, UserManager.getInstance().getUid());

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadDataInfo(AppConst.LOAD_TYPE_UP, UserManager.getInstance().getUid());
            }
        });
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);
    }

    private void createP() {
        mPresenter = new GroundPresenter();
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
    public void onJumpToProfile(int uid) {
        ProfileActivity.start(mContext ,uid);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_post) {
            if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
                LoginActivity.start(mContext);
                return ;
            }
            PostBlogActivity.start(mContext);
        }

    }

    @Override
    public void showLoadMoreBlogData(List<Blog> blog) {
        mAdapter.addAll(blog);
    }

    @Override
    public void showBlogDataView(List<Blog> blog) {
        mAdapter.setData(blog);
    }

    @Override
    public void showAdminPost(Blog blog) {
        // 暂时只有一条
        blog.type = 1;
        mAdapter.insertFirst(blog);
    }

    @Override
    public void showAdminPostErrorView() {
        Log.e(TAG, "showAdminPostErrorView: 公告请求失败");
    }

    @Override
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {
        if (iwHelper != null) {
            iwHelper.show(i ,imageGroupList ,urlList);
        }
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
