package com.yunzhou.tdinformation.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.blog.presenter.MyPostBlogPresenter;
import com.yunzhou.tdinformation.blog.presenter.MyPostBlogView;
import com.yunzhou.tdinformation.community.adapter.BlogAdapter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.profile.ProfileActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.blog.GlideSimpleLoader;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   MyPostBlogActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 17:02
 *  @描述：
 */

public class MyPostBlogActivity extends BaseActivity<MyPostBlogPresenter> implements MessagePicturesLayout.Callback, BlogAdapter.OnBlogClickListener,
        MyPostBlogView, View.OnClickListener {

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
    private MyPostBlogPresenter mPresenter;
    private ImageWatcherHelper iwHelper;
    private BlogAdapter mAdapter;
    private View mLoadingView;
    private View mStatusView;

    @Override
    protected int getContentView() {
        return R.layout.activity_common_refresh_list;
    }

    @Override
    protected MyPostBlogPresenter createP(Context context) {
        mPresenter = new MyPostBlogPresenter();
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
        mTvTitle.setText(R.string.my_post);
        initRecyclerView();
        initRefreshLayout();
        initImageWatch();
    }

    @Override
    protected void initData() {
        mPresenter.loadBlogListData(MyPostBlogActivity.this, AppConst.LOAD_TYPE_NORMAL);
    }

    private void initRecyclerView() {
        mAdapter = new BlogAdapter(this, BlogAdapter.TYPE_MY_POST).setPictureClickCallback(this);
        mAdapter.setOnBlogClickListener(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    private void initImageWatch() {
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader())
                .setTranslucentStatus(25)
                .setErrorImageRes(R.mipmap.error_picture);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadBlogListData(MyPostBlogActivity.this, AppConst.LOAD_TYPE_UP);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadBlogListData(MyPostBlogActivity.this, AppConst.LOAD_TYPE_DOWN);
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
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {
        iwHelper.show(i, imageGroupList, urlList);
    }

    @Override
    public boolean onCollectClick(int preIsCollect, int pos) {
        if (!UserManager.getInstance().isLogin()) {
            LoginActivity.start(this);
            return false;
        } else {
            Blog item = mAdapter.getItem(pos);
            if (item != null) {
                mPresenter.addCollect(this, item.getPostId(), preIsCollect);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onLikeClick(int preIsLike, int pos) {
        if (!UserManager.getInstance().isLogin()) {
            LoginActivity.start(this);
            return false;
        } else {
            Blog item = mAdapter.getItem(pos);
            if (item != null) {
                mPresenter.insertLike(this, item.getPostId() ,preIsLike);
                return true;
            }
            return false;
        }
    }

    @Override
    public void onCommentClick(int pos) {
        Blog blog = mAdapter.getItem(pos);
        if (blog != null){
            BlogDetailActivity.start(this ,blog.getPostId());
        }
    }

    @Override
    public void onDeleteClick(int pos) {
        Blog item = mAdapter.getItem(pos);
        if (item != null) {
            mPresenter.deleteBlog(this ,item);
        }
    }

    @Override
    public void showEmptyData() {
        showEmptyView(View.VISIBLE);
    }
    @Override
    public void onJumpToProfile(int uid) {
        ProfileActivity.start(this ,uid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadBlogListData(this, AppConst.LOAD_TYPE_NORMAL);
                break;
            default:

                break;
        }
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
    public void showLoadMoreBlogData(List<Blog> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void showBlogDataView(List<Blog> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showCollectSuccess(int preIsCollect) {
        ToastUtil.showShort(this, preIsCollect == 0 ? "收藏成功" : "取消收藏成功");
    }

    @Override
    public void showCollectError(int postId) {
        // TODO: 2018/11/3  优化用户体验
    }

    @Override
    public void showLikeSuccess(int preIsLike) {
        ToastUtil.showShort(this, preIsLike == 0 ? "点赞成功" : "取消点赞成功");
    }

    @Override
    public void showLikeError(int postId) {
        // TODO: 2018/11/3  优化用户体验
    }

    @Override
    public void showDeleteSuccess(Blog blog) {
        mAdapter.remove(blog);
    }

    @Override
    public void showDeleteError(String message) {

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
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MyPostBlogActivity.class);
        context.startActivity(starter);
    }
}
