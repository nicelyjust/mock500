package com.yunzhou.tdinformation.blog;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   BlogDetailActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 16:22
 *  @描述：    帖子详情界面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.FastClickUtil;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.blog.CommentChildBean;
import com.yunzhou.tdinformation.bean.blog.PostCommentEntity;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.blog.adapter.BlogDetailAdapter;
import com.yunzhou.tdinformation.blog.presenter.BlogDetailPresenter;
import com.yunzhou.tdinformation.blog.view.BlogDetailView;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.profile.ProfileActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.Comment.CommentBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BlogDetailActivity extends BaseActivity<BlogDetailPresenter> implements BlogDetailView,
        BlogDetailAdapter.Callback, View.OnClickListener {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_blog_detail)
    RecyclerView mRv;
    @BindView(R.id.srl_blog_detail)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vs_empty_blog_detail)
    ViewStub mVsEmpty;
    @BindView(R.id.vs_load)
    ViewStub mVsLoad;
    @BindView(R.id.fl_bottom_container)
    FrameLayout mFlBottom;

    private BlogDetailPresenter mPresenter;
    private int mPostId;
    private BlogDetailAdapter mAdapter;
    private View mStatusView;
    private Blog mBlog;
    private CommentBar mDelegation;
    private View mLoadingView;

    @Override
    protected int getContentView() {
        return R.layout.activity_blog_detail;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("帖子详情");

        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mAdapter = new BlogDetailAdapter(this, this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadData(AppConst.LOAD_TYPE_UP, mPostId);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.loadData(AppConst.LOAD_TYPE_DOWN, mPostId);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            // TODO: 2018/10/11 setResult
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected BlogDetailPresenter createP(Context context) {
        mPresenter = new BlogDetailPresenter();
        return mPresenter;
    }

    @Override
    protected void initData() {
        mPostId = getIntent().getIntExtra(AppConst.Extra.POST_ID, -1);
        showLoading();
        mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mPostId);
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
    public void showBlogDataView(List<PostCommentEntity> data, Blog blog) {
        mBlog = blog;
        initBottomBar(blog);
        for (PostCommentEntity postCommentEntity : data) {
            List<CommentChildBean> commentBeans = postCommentEntity.getCommentBeans();
            for (CommentChildBean commentBean : commentBeans) {
                commentBean.build(this);
            }
        }
        blog.init();
        mAdapter.setData(data, blog);
    }

    @Override
    public void showLoadMoreCommentData(List<PostCommentEntity> postCommentList) {
        for (PostCommentEntity postCommentEntity : postCommentList) {
            List<CommentChildBean> commentBeans = postCommentEntity.getCommentBeans();
            for (CommentChildBean commentBean : commentBeans) {
                commentBean.build(this);
            }
        }
        mAdapter.addAll(postCommentList);
    }

    @Override
    public void showNoCommentsView(Blog blog) {
        mBlog = blog;
        initBottomBar(blog);
        blog.init();
        mAdapter.setData(null, blog);
    }

    @Override
    public void onFollowClick(Blog blog) {
        if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
            LoginActivity.start(this);
            return;
        }
        mPresenter.requestFollow(this, blog.getIsCheck() == 1, blog.getUid());
    }

    @Override
    public void onCommentLikeClick(int position, int preIsLike, int commentsId) {
        if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
            LoginActivity.start(this);
        } else {
            mPresenter.insertLikeComment(this, position, preIsLike, commentsId);
        }
    }

    @Override
    public void onCommentClick(int position, CommentChildBean commentChildBean) {
        mDelegation.setCommentHint("回复@" + commentChildBean.getChildUserName());
        mDelegation.performClick(position ,commentChildBean);
    }

    @Override
    public void onItemClick(int position) {
        mDelegation.setCommentHint("添加评论");
        mDelegation.performClick(position ,mAdapter.getCommentItem(position));
    }

    @Override
    public void onHeadClick(int uid) {
        if (UserManager.getInstance().isLogin()) {
            ProfileActivity.start(this, uid);
        } else {
            LoginActivity.start(this);
        }

    }
    @Override
    public void showCollectSuccess(int preIsCollect) {
        mBlog.setIsCollect(preIsCollect == 1 ? 0 : 1);
        mBlog.setCollectNum(preIsCollect == 1 ? mBlog.getCollectNum() - 1 : mBlog.getCollectNum() + 1);
        mDelegation.setFavDrawable(preIsCollect == 1 ? R.mipmap.un_collect : R.mipmap.collected);
        mDelegation.showCollectCount(mBlog.getCollectNum(), mBlog.getIsCollect() == 1);
    }

    @Override
    public void showCollectError(int preIsCollect) {
        ToastUtil.showShort(this, "操作失败");
    }

    @Override
    public void showLikeSuccess(int preIsLike) {
        mBlog.setIsLike(preIsLike == 1 ? 0 : 1);
        mBlog.setLikeNum(preIsLike == 1 ? mBlog.getLikeNum() - 1 : mBlog.getLikeNum() + 1);
        mDelegation.getLikeImage().setImageResource(preIsLike == 1 ? R.mipmap.un_like : R.mipmap.liked);
        mDelegation.showLikeCount(mBlog.getLikeNum(), mBlog.getIsLike() == 1);
    }

    @Override
    public void showLikeError() {
        ToastUtil.showShort(this, "操作失败");
    }

    @Override
    public void showFollowSuccess() {
        int isCheck = mBlog.getIsCheck();
        mBlog.setIsCheck(isCheck == 0 ? 1 : 0);
        mAdapter.notifyItemChanged(0, 1);
    }

    @Override
    public void showFollowError(String message) {
        ToastUtil.showShort(this, "操作失败");
    }

    @Override
    public void insertLikeCommentOk(int position, int preIsLike) {
        PostCommentEntity commentItem = mAdapter.getCommentItem(position);
        if (commentItem != null) {
            commentItem.setIsLiked(preIsLike == 1 ? 0 : 1);
            commentItem.setLikeNum(preIsLike == 1 ? commentItem.getLikeNum() - 1 : commentItem.getLikeNum() + 1);
            mAdapter.notifyItemChanged(position + 1, 2);
        }
    }

    @Override
    public void insertLikeCommentError(String message) {
        ToastUtil.showShort(this, "操作失败,请重试");
    }

    @Override
    public void showAddMainComment(String content) {
        mDelegation.getBottomSheet().dismiss();
        PostCommentEntity item = mPresenter.buildPostCommentEntity(content);
        mAdapter.addItemFirst(item);
    }

    @Override
    public void showAddComment(int position, CommentChildBean commentChildBean) {
        mDelegation.getBottomSheet().dismiss();
        PostCommentEntity commentItem = mAdapter.getCommentItem(position);
        if (commentItem != null) {
            List<CommentChildBean> commentBeans = commentItem.getCommentBeans();
            if (commentBeans == null){
                ArrayList<CommentChildBean> commentBeans1 = new ArrayList<>(1);
                commentBeans1.add(mPresenter.buildCommentChildBean(commentChildBean));
                commentItem.setCommentBeans(commentBeans1);
            } else {
                commentBeans.add(mPresenter.buildCommentChildBean(commentChildBean));
            }
            mAdapter.notifyItemChanged(position + 1, 3);
        }
    }

    @Override
    public void showAddChildComment(int position) {
        mDelegation.getBottomSheet().dismiss();
        PostCommentEntity item = mAdapter.getCommentItem(position);
        if (item != null) {
            List<CommentChildBean> commentBeans = item.getCommentBeans();
            if (commentBeans == null){
                ArrayList<CommentChildBean> commentBeans1 = new ArrayList<>(1);
                commentBeans1.add(mPresenter.buildCommentChildBean());
                item.setCommentBeans(commentBeans1);
            } else {
                commentBeans.add(mPresenter.buildCommentChildBean());
            }
            mAdapter.notifyItemChanged(position + 1, 3);
        }
    }

    @Override
    public void showCommentFailed(String msg) {
        ToastUtil.showShort(this ,"评论失败,请重试");
    }

    private void initBottomBar(Blog blog) {
        mDelegation = CommentBar.delegation(this, mFlBottom);

        mDelegation.showCommentCount(blog.getCommitNum());
        mDelegation.showCollectCount(blog.getCollectNum(), blog.getIsCollect() == 1);
        mDelegation.showLikeCount(blog.getLikeNum(), blog.getIsLike() == 1);
        mDelegation.getLikeImage().setImageResource(blog.getIsLike() == 0 ? R.mipmap.un_like : R.mipmap.liked);
        mDelegation.setLikeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlogDetailActivity.this.onClick(v);
            }
        });
        // 设置收藏
        mDelegation.setFavDrawable(blog.getIsCollect() == 0 ? R.mipmap.un_collect : R.mipmap.collected);
        mDelegation.setFavListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlogDetailActivity.this.onClick(v);
            }
        });
        mDelegation.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    handleKeyDel();
                }
                return false;
            }
        });


        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mDelegation.getBottomSheet().getCommentText().replaceAll("[\\s\\n]+", " ");
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShort(BlogDetailActivity.this, "请输入文字");
                    return;
                }
                if (!UserManager.getInstance().isLogin()) {
                    LoginActivity.start(BlogDetailActivity.this);
                    return;
                }
                mPresenter.sendComment(BlogDetailActivity.this , mDelegation.getPosition(),mDelegation.getCommentItem(),mDelegation.getCommentChildBean(),content);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_empty:
                // 错误视图 重新加载
                mPresenter.loadData(AppConst.LOAD_TYPE_NORMAL, mPostId);
                break;
            case R.id.rl_like_area:
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(this, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                    return;
                }
                if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
                    LoginActivity.start(this);
                    return;
                }
                if (!FastClickUtil.isFastClick())
                    mPresenter.insertLike(this, mPostId, mBlog.getIsLike());
                break;
            case R.id.rl_collect_area:
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(this, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                    return;
                }
                if (UserManager.getInstance().getUid() == UserManager.UN_LOGIN) {
                    LoginActivity.start(this);
                    return;
                }
                if (!FastClickUtil.isFastClick())
                    mPresenter.addCollect(this, mBlog.getPostId(), mBlog.getIsCollect());
                break;
            default:

                break;
        }
    }

    private void handleKeyDel() {
        // 暂不处理键盘的完成事件
    }
    @Override
    public void showError(int visibility) {
        if (visibility == View.GONE) {
            if (mStatusView == null) {
                return;
            } else {
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
        }
        mStatusView.setVisibility(visibility);
    }

    @Override
    public void showLoading() {
        if (mLoadingView == null)
            mLoadingView = mVsLoad.inflate();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.GONE);
    }

    public static void start(Context context, int postId) {
        Intent starter = new Intent(context, BlogDetailActivity.class);
        starter.putExtra(AppConst.Extra.POST_ID, postId);
        context.startActivity(starter);
    }

}
