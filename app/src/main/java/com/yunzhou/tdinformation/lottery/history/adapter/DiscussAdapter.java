package com.yunzhou.tdinformation.lottery.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.adapter
 *  @文件名:   DiscussAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 15:52
 *  @描述：
 */

public class DiscussAdapter extends BaseRvAdapter<GeneralCommentBean> {

    private Callback mCallback;

    public DiscussAdapter(Context context, DiscussAdapter.Callback callback) {
        super(context);
        mCallback = callback;
    }


    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_discuss, parent, false);
        return new VH(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, GeneralCommentBean item, int position) {
        if (holder instanceof VH) {
            VH vh = (VH) holder;
            int type = item.type;
            if (position == 0) {
                vh.mTvTypeName.setVisibility(View.VISIBLE);
                if (type == 1) {
                    vh.mTvTypeName.setText("最热评论" + "(" + item.size + ")");
                } else {
                    vh.mTvTypeName.setText("最新评论" + "(" + item.size + ")");
                }
            } else {
                GeneralCommentBean preComment = mItems.get(position - 1);
                vh.mTvTypeName.setVisibility(type != preComment.type ? View.VISIBLE : View.GONE);
                vh.mTvTypeName.setText("最新评论" + "(" + item.size + ")");
            }
            Glide.with(holder.itemView.getContext()).load(item.getAvatar())
                    .apply(new RequestOptions().placeholder(R.mipmap.default_head).error(R.mipmap.default_head).circleCrop()).into(vh.mIvProfileComment);
            vh.mTvNickname.setText(item.getNickName());
            vh.mTvPostTime.setText(TimeUtils.getStandardDate(1000 * item.getReleaseTime()));
            vh.mTvDetail.setText(item.getCommentContent());
            setLike(vh, position, item);
        }
    }

    private void setLike(final VH holder, final int position, final GeneralCommentBean item) {
        holder.mTvLikeComment.setText(String.valueOf(item.getPraiseNum()));
        holder.mTvLikeComment.setTextColor(mContext.getResources().getColor(item.getIsPraise() == 0 ?
                R.color.colorText9 : R.color.common_orange));
        holder.mTvLikeComment.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getIsPraise() == 0 ?
                R.mipmap.un_like : R.mipmap.liked, 0, 0, 0);
        holder.mTvLikeComment.setTag(position);
        holder.mTvLikeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                int pos = (int) v.getTag();
                GeneralCommentBean commentBean = mItems.get(pos);
                int preIsLike = commentBean.getIsPraise();
                if (mCallback != null) {
                    if (!mCallback.onCommentLikeClick(commentBean)) {
                        return;
                    }
                }
                commentBean.setIsPraise(preIsLike == 1 ? 0 : 1);
                commentBean.setPraiseNum(preIsLike == 0 ? commentBean.getPraiseNum() + 1 : commentBean.getPraiseNum() - 1);
                holder.mTvLikeComment.setText(String.valueOf(commentBean.getPraiseNum()));
                holder.mTvLikeComment.setCompoundDrawablesRelativeWithIntrinsicBounds(commentBean.getIsPraise() == 0 ?
                        R.mipmap.un_like : R.mipmap.liked, 0, 0, 0);
            }
        });
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_discuss_type_name)
        TextView mTvTypeName;
        @BindView(R.id.iv_profile_comment)
        ImageView mIvProfileComment;
        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
        @BindView(R.id.tv_post_time)
        TextView mTvPostTime;
        @BindView(R.id.tv_like_comment)
        TextView mTvLikeComment;
        @BindView(R.id.tv_detail)
        TextView mTvDetail;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        boolean onCommentLikeClick(GeneralCommentBean commentBean);
    }
}
