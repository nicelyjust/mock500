package com.yunzhou.tdinformation.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.yunzhou.tdinformation.blog.presenter.BlogListPresenter;
import com.yunzhou.tdinformation.blog.view.BlogListView;
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
 *  @文件名:   BlogListActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 12:51
 *  @描述：    帖子列表界面
 */

public class BlogListActivity extends BaseActivity<BlogListPresenter> implements BlogListView, MessagePicturesLayout.Callback, BlogAdapter.OnBlogClickListener, View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_blog_list)
    RecyclerView mRv;
    @BindView(R.id.srl_blog_list)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fab_post)
    FloatingActionButton mFabPost;
    @BindView(R.id.vs_empty_blog_list)
    ViewStub mVsEmpty;
    private BlogListPresenter mPresenter;
    private BlogAdapter mAdapter;
    private String mCircleType;
    private ImageWatcherHelper iwHelper;
    private View mStatusView;

    @Override
    protected int getContentView() {
        return R.layout.activity_blog_list;
    }

    @Override
    protected BlogListPresenter createP(Context context) {
        mPresenter = new BlogListPresenter();
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
        mTvTitle.setText(getIntent().getStringExtra(AppConst.Extra.CIRCLE_TYPE_NAME));
        mFabPost.setOnClickListener(this);
        initRefreshLayout();
        initImageWatch();
    }

    private void initImageWatch() {
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(25) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture);
    }

    private void initRefreshLayout() {
        mAdapter = new BlogAdapter(this ,BlogAdapter.TYPE_OTHER).setPictureClickCallback(this);
        mAdapter.setOnBlogClickListener(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadBlogListData(AppConst.LOAD_TYPE_UP, mCircleType);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadBlogListData(AppConst.LOAD_TYPE_DOWN, mCircleType);
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
        super.initData();
        mCircleType = getIntent().getStringExtra(AppConst.Extra.CIRCLE_TYPE);
        if (!mRefreshLayout.autoRefresh()) {
            mPresenter.loadBlogListData(AppConst.LOAD_TYPE_NORMAL, mCircleType);
        }
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
    }

    @Override
    public void showEmptyData() {

    }

    @Override
    public void onJumpToProfile(int uid) {
        ProfileActivity.start(this ,uid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_post:
                PostBlogActivity.start(this);
                break;
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mPresenter.loadBlogListData(AppConst.LOAD_TYPE_NORMAL, mCircleType);
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
    public void showBlogDataView(List<Blog> blogList) {
        mAdapter.setData(blogList);
    }
    @Override
    public void showEmptyView(int visibility) {
        if (visibility == View.GONE) {
            if (mStatusView == null) {
                return;
            }else{
                mStatusView.setVisibility(View.GONE);
            }
        }
        if (mStatusView == null) {
            mStatusView = mVsEmpty.inflate();
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
            }else{
                mStatusView.setVisibility(View.GONE);
            }
        }
        if (mStatusView == null) {
            mStatusView = mVsEmpty.inflate();
        }
        TextView textView = mStatusView.findViewById(R.id.tv_empty_tip);
        ImageView imageView = mStatusView.findViewById(R.id.iv_empty);
        imageView.setImageResource(R.mipmap.error);
        textView.setText(R.string.net_error);
        mStatusView.findViewById(R.id.iv_empty).setOnClickListener(this);
        mStatusView.setVisibility(visibility);
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public static void start(Context context, String circleType, String circleName) {
        Intent starter = new Intent(context, BlogListActivity.class);
        starter.putExtra(AppConst.Extra.CIRCLE_TYPE, circleType);
        starter.putExtra(AppConst.Extra.CIRCLE_TYPE_NAME, circleName);
        context.startActivity(starter);
    }
}
