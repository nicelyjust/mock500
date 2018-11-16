package com.yunzhou.tdinformation.lottery.history;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.Comment.BottomSheetBar;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view.Comment
 *  @创建者:   lz
 *  @创建时间:  2018/10/14 12:38
 *  @修改时间:  nicely 2018/10/14 12:38
 *  @描述：    评论底部接管栏
 */
@SuppressWarnings("all")
public class DiscussCommentBar {

    private Context mContext;
    private View mRootView;
    private ViewGroup mParent;
    private TextView mTextCommentCount;
    private TextView mCommentText;
    private BottomSheetBar mDelegation;
    private RelativeLayout mCommentLayout;


    private DiscussCommentBar(Context context) {
        this.mContext = context;
    }

    public static DiscussCommentBar delegation(Context context, ViewGroup parent) {
        DiscussCommentBar bar = new DiscussCommentBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_discuss_comment_bar, parent, false);
        bar.mParent = parent;
        bar.mDelegation = BottomSheetBar.delegation(context);
        bar.mParent.addView(bar.mRootView);
        bar.initView();
        return bar;
    }

    private void initView() {
        mCommentText = (TextView) mRootView.findViewById(R.id.tv_comment_detail);
        mTextCommentCount = (TextView) mRootView.findViewById(R.id.tv_comment_count);
        mCommentLayout = (RelativeLayout) mRootView.findViewById(R.id.ll_comment_area);
        mCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    CharSequence text = mCommentText.getHint();
                    mDelegation.show(TextUtils.isEmpty(text) ? "" : text.toString());
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
    }

    public void setCommentHint(String text) {
        mCommentText.setHint(text);
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
                mTextCommentCount.setText(0 + "评");
            } else {
                mTextCommentCount.setText(count + "评");
            }
        }
    }

    public TextView getCommentText() {
        return mCommentText;
    }

    public TextView getCommentCountText() {
        return mTextCommentCount;
    }

    public void performClick() {
        mCommentLayout.performClick();
    }

}
