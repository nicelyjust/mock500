package com.yunzhou.tdinformation.mine.follow;

/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.follow
 *  @文件名:   MineFollowAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 10:24
 *  @描述：
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.FollowEntity;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFollowAdapter extends RecyclerView.Adapter<MineFollowAdapter.FollowViewHolder> {

    private Context mContext;
    private Callback mCallback;
    private List<FollowEntity.FollowBean> mItems;

    public MineFollowAdapter(Context context ,Callback callback) {
        mContext = context;
        mCallback = callback;
        mItems = new ArrayList<>();
    }

    public void setData(List<FollowEntity.FollowBean> items) {
        if (items != null) {
            int previousSize = mItems.size();
            this.mItems.clear();
            notifyItemRangeRemoved(0, previousSize);
            this.mItems.addAll(items);
            notifyItemRangeChanged(0, mItems.size());
        }
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow, parent, false);
        return new FollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder vh, int position, @NonNull List<Object> payloads) {
        if (mItems.size() <= position){
            return;
        }
        FollowEntity.FollowBean followBean = mItems.get(position);
        boolean isMine = UserManager.getInstance().isMine(followBean.getUid());
        int status = isMine ? followBean.getStatus() : followBean.getMyAttentionStatus();
        if (payloads.size() > 0) {
            setWatchView(vh, status);
        } else {
            vh.mVBlockGray.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            Glide.with(mContext).load(followBean.getAvatar()).apply(new RequestOptions().error(R.mipmap.default_head).centerCrop()).into(vh.mIvHead);
            // 没有字段判断
            vh.mIvExpertTag.setVisibility(View.GONE);
            vh.mTvIntro.setText(followBean.getIntro());
            vh.mTvName.setText(followBean.getNickName());
            setWatchView(vh, status);
            vh.mTvFollow.setTag(position);
            vh.mTvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    FollowEntity.FollowBean bean = mItems.get(pos);
                    if (bean != null && mCallback != null) {
                        int followedUserUid = bean.getFollowedUserUid();
                        int status = UserManager.getInstance().isMine(bean.getUid()) ? bean.getStatus() : bean.getMyAttentionStatus();
                        mCallback.onFollowClick(pos, followedUserUid, status);
                    }
                }
            });
        }

    }

    private void setWatchView(@NonNull FollowViewHolder vh, int status) {
        vh.mTvFollow.setBackgroundResource(status == 1 ? R.drawable.shape_gray_has_follow_bg : R.drawable.shape_red_follow_bg);
        vh.mTvFollow.setText(status == 1 ? R.string.has_follow : R.string.follow_plus);
        vh.mTvFollow.setTextColor(mContext.getResources().getColor(status == 1 ? R.color.colorText9 : R.color.white));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<FollowEntity.FollowBean> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }
    public final void removeItem(FollowEntity.FollowBean item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    @Nullable
    public FollowEntity.FollowBean getItem(int position){
        try {
            return mItems.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    static class FollowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_block_gray)
        View mVBlockGray;
        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.iv_expert_tag)
        ImageView mIvExpertTag;
        @BindView(R.id.fl_img_area)
        FrameLayout mFlImgArea;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.tv_intro)
        TextView mTvIntro;

        public FollowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onFollowClick(int pos, int followedUserUid, int status);
    }
}
