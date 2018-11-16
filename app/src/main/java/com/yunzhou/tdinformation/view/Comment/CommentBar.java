package com.yunzhou.tdinformation.view.Comment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.blog.CommentChildBean;
import com.yunzhou.tdinformation.bean.blog.PostCommentEntity;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.user.UserManager;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view.Comment
 *  @创建者:   lz
 *  @创建时间:  2018/10/14 12:38
 *  @修改时间:  nicely 2018/10/14 12:38
 *  @描述：    评论底部接管栏
 */
@SuppressWarnings("all")
public class CommentBar {

    private Context mContext;
    private View mRootView;
    private FrameLayout mFrameLayout;
    private ViewGroup mParent;
    // 收藏按钮
    private ImageView mFavView;
    private TextView mTextCommentCount;
    private TextView mCommentText;
    private BottomSheetBar mDelegation;
    private RelativeLayout mCommentLayout;
    // 喜欢按钮
    private ImageView mImageLike;
    private RelativeLayout mLikeLayout;
    private RelativeLayout mCollectLayout;
    private TextView mTvLikeCount;
    private TextView mTvCollectCount;
    /**
     * 记录位置
     */
    private int mPosition = -1;
    /**
     * 代表是点击回复子评论
     */
    private CommentChildBean mCommentChildBean;
    /**
     * 代表是点击回复评论
     */
    private PostCommentEntity mCommentItem;

    public int getPosition() {
        return mPosition;
    }

    public CommentChildBean getCommentChildBean() {
        return mCommentChildBean;
    }

    public PostCommentEntity getCommentItem() {
        return mCommentItem;
    }

    private CommentBar(Context context) {
        this.mContext = context;
    }

    public static CommentBar delegation(Context context, ViewGroup parent) {
        CommentBar bar = new CommentBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_comment_bar, parent, false);
        bar.mParent = parent;
        bar.mDelegation = BottomSheetBar.delegation(context);
        bar.mParent.addView(bar.mRootView);
        bar.initView();
        return bar;
    }

    private void initView() {
        mFavView = (ImageView) mRootView.findViewById(R.id.ib_collect);
        mCommentText = (TextView) mRootView.findViewById(R.id.tv_comment);
        mTextCommentCount = (TextView) mRootView.findViewById(R.id.tv_comment_count);
        mCommentLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_comment_area);
        mLikeLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_like_area);
        mTvLikeCount = (TextView) mRootView.findViewById(R.id.tv_like_count);
        mCollectLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_collect_area);
        mTvCollectCount = (TextView) mRootView.findViewById(R.id.tv_collect_count);
        mImageLike = (ImageView) mRootView.findViewById(R.id.ib_like);
        mCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    reset();
                    CharSequence text = mCommentText.getHint();
                    mDelegation.show(TextUtils.isEmpty(text) ? "添加评论" : text.toString());
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mCommentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    reset();
                    CharSequence text = mCommentText.getHint();
                    mDelegation.show(TextUtils.isEmpty(text) ? "添加评论" : text.toString());
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
    }

    /**
     * favorite the detail
     *
     * @param listener
     */
    public void setFavListener(View.OnClickListener listener) {
        mCollectLayout.setOnClickListener(listener);
    }

    public void setCommentListener(View.OnClickListener listener) {
        mCommentText.setOnClickListener(listener);
    }

    public void setCommentHint(String text) {
        mCommentText.setHint(text);
    }

    public void setFavDrawable(int drawable) {
        mFavView.setImageResource(drawable);
    }

    public BottomSheetBar getBottomSheet() {
        return mDelegation;
    }

    public void setCommitButtonEnable(boolean enable) {
        mDelegation.getBtnCommit().setEnabled(enable);
    }

    public void showCommentCount(int count) {
        if (mTextCommentCount != null) {
            if (count <= 0) {
                mTextCommentCount.setVisibility(View.GONE);
            } else {
                mTextCommentCount.setText(String.valueOf(count));
            }
        }
    }

    public void showLikeCount(int count,boolean isLike) {
        if (mTvLikeCount != null) {
            if (count <= 0) {
                mTvLikeCount.setText("0");
            } else if (count >= 1000) {
                mTvLikeCount.setText("999+");
            } else {
                mTvLikeCount.setText(String.valueOf(count));
            }
            mTvLikeCount.setTextColor(isLike ? mContext.getResources().getColor(R.color.c_F57004) :  mContext.getResources().getColor(R.color.colorText9));
        }
    }

    public void showCollectCount(int count,boolean isCollect) {
        if (mTvCollectCount != null) {
            if (count <= 0) {
                mTvCollectCount.setText("0");
            } else if (count >= 1000) {
                mTvCollectCount.setText("999+");
            } else {
                mTvCollectCount.setText(String.valueOf(count));
            }
            mTvCollectCount.setTextColor(isCollect ? mContext.getResources().getColor(R.color.c_F64A50) :  mContext.getResources().getColor(R.color.colorText9));
        }
    }

    public ImageView getLikeImage() {
        return mImageLike;
    }


    public void setLikeListener(View.OnClickListener likeListener) {
        mLikeLayout.setOnClickListener(likeListener);
    }

    public TextView getCommentText() {
        return mCommentText;
    }

    public TextView getCommentCountText() {
        return mTextCommentCount;
    }

    public void performClick(int position, @Nullable CommentChildBean commentChildBean) {
        this.mPosition = position;
        this.mCommentChildBean = commentChildBean;
        this.mCommentItem = null;
        if (UserManager.getInstance().isLogin()) {
            CharSequence text = mCommentText.getHint();
            mDelegation.show(TextUtils.isEmpty(text) ? "添加评论" : text.toString());
        } else {
            LoginActivity.start(mContext);
        }
    }

    public void performClick(int position,@Nullable PostCommentEntity commentItem) {
        this.mPosition = position;
        this.mCommentItem = commentItem;
        this.mCommentChildBean = null;
        if (UserManager.getInstance().isLogin()) {
            CharSequence text = mCommentText.getHint();
            mDelegation.show(TextUtils.isEmpty(text) ? "添加评论" : text.toString());
        } else {
            LoginActivity.start(mContext);
        }
    }
    private void reset(){
        this.mPosition = -1;
        this.mCommentItem = null;
        this.mCommentChildBean = null;
    }
}
