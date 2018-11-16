package com.yunzhou.tdinformation.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.blog.BlogDetailActivity;
import com.yunzhou.tdinformation.community.adapter.BlogAdapter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.mine.fans.MineFanActivity;
import com.yunzhou.tdinformation.mine.follow.MineFollowActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.blog.GlideSimpleLoader;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;
import com.yunzhou.tdinformation.view.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.profile
 *  @文件名:   ProfileActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/6 10:29
 *  @描述：
 */
public class ProfileActivity extends BaseCommonAct implements ProfileView,
        BlogAdapter.OnBlogClickListener, MessagePicturesLayout.Callback, View.OnClickListener {

    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.tv_follow_num)
    TextView mTvFollowNum;
    @BindView(R.id.ll_follow_area)
    LinearLayout mLlFollowArea;
    @BindView(R.id.tv_fan_num)
    TextView mTvFanNum;
    @BindView(R.id.ll_fan_area)
    LinearLayout mLlFanArea;
    @BindView(R.id.btn_follow)
    Button mBtnFollow;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.rv_profile)
    RecyclerView mRv;
    @BindView(R.id.srl_profile)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_status)
    ViewStub mVsStatus;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    private ProfileController mController;
    private UserEntity mEntity;
    private BlogAdapter mAdapter;
    private ImageWatcherHelper iwHelper;
    private View mLoadingView;
    private View mStatusView;
    private int mUid;
    private boolean mIsFollow;

    @Override
    protected int getContentView() {
        return R.layout.activity_profile_bak;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mController = new ProfileController(this);
        initRecyclerView();
        initRefreshLayout();
        initImageWatch();
    }

    @Override
    protected void initWindow() {
        Utils.setStatusBarColor(this, getResources().getColor(R.color.base_ED7A01));
    }

    @Override
    protected void initData() {
        mUid = getIntent().getIntExtra(AppConst.Extra.USER_ID, 0);
        mController.loadUserInfo(this, mUid);
        if (!UserManager.getInstance().isMine(mUid))
            mController.checkFollow(this, mUid);
        mController.loadBlogListData(this, AppConst.LOAD_TYPE_NORMAL, mUid);
    }

    private void initRecyclerView() {
        mAdapter = new BlogAdapter(this, BlogAdapter.TYPE_OTHER).setPictureClickCallback(this);
        mAdapter.setOnBlogClickListener(this);
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                mController.loadBlogListData(ProfileActivity.this, AppConst.LOAD_TYPE_UP, mUid);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mController.loadBlogListData(ProfileActivity.this, AppConst.LOAD_TYPE_DOWN, mUid);
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
                mController.addCollect(this, item.getPostId(), preIsCollect);
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
                mController.insertLike(this, item.getPostId(), preIsLike);
                return true;
            }
            return false;
        }
    }

    @Override
    public void onCommentClick(int pos) {
        Blog blog = mAdapter.getItem(pos);
        if (blog != null) {
            BlogDetailActivity.start(this, blog.getPostId());
        }
    }

    @Override
    public void onDeleteClick(int pos) {
    }

    @Override
    public void showEmptyData() {
        showEmptyView(View.VISIBLE);
    }

    @Override
    public void onJumpToProfile(int uid) {
        if (!UserManager.getInstance().isMine(uid))
            ProfileActivity.start(this ,uid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                showErrorView(View.GONE);
                mController.loadBlogListData(this, AppConst.LOAD_TYPE_NORMAL, mUid);
                break;
            default:
                break;
        }
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
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    @Override
    public void showUserInfo(UserEntity entity) {
        this.mEntity = entity;
        Glide.with(this).load(entity.getAvatar()).apply(new RequestOptions().error(R.mipmap.default_head)).into(mIvHead);
        mTvNickname.setText(entity.getNickName());
        mTvIntro.setText(TextUtils.isEmpty(entity.getIntro()) ? "个人简介：这个人很懒,什么也没留下" : "个人简介：" + entity.getIntro());
        mTvFanNum.setText(String.valueOf(entity.getFansCount()));
        mTvFollowNum.setText(String.valueOf(entity.getAttentionCount()));
        mBtnFollow.setVisibility(UserManager.getInstance().isMine(entity.getId()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showFollowSuccess(boolean hasFollow, String msg) {
        this.mIsFollow = !mIsFollow;
        mBtnFollow.setText(hasFollow ? R.string.follow_plus : R.string.has_follow);
    }

    @Override
    public void showFollowError(boolean hasFollow, String message) {
        ToastUtil.showShort(this, "操作失败");
    }

    @Override
    public void showFollowView(boolean isFollow) {
        this.mIsFollow = isFollow;
        mBtnFollow.setVisibility(View.VISIBLE);
        mBtnFollow.setText(isFollow ? R.string.has_follow : R.string.follow_plus);
    }

    @OnClick({R.id.ll_follow_area, R.id.ll_fan_area, R.id.btn_follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_follow_area:
                MineFollowActivity.start(this, mUid);
                break;
            case R.id.ll_fan_area:
                MineFanActivity.start(this, mUid);
                break;
            case R.id.btn_follow:
                mController.requestFollowUser(this, mIsFollow, mEntity.getId());
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

    public static void start(Context context, int uid) {
        Intent starter = new Intent(context, ProfileActivity.class);
        starter.putExtra(AppConst.Extra.USER_ID, uid);
        context.startActivity(starter);
    }
}
