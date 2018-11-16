package com.yunzhou.tdinformation.mine.fans;

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
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.FanEntity;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFanAdapter extends RecyclerView.Adapter<MineFanAdapter.FollowViewHolder> {

    private Context mContext;
    private Callback mCallback;
    private List<FanEntity.FanBean> mItems;

    public MineFanAdapter(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
        mItems = new ArrayList<>();
    }

    public void setData(List<FanEntity.FanBean> items) {
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
        if (mItems.size() <= position) {
            return;
        }
        FanEntity.FanBean followBean = mItems.get(position);
        int status = followBean.getMyAttentionStatus();
        if (payloads.size() > 0) {
            setWatchView(vh, status, UserManager.getInstance().isMine(followBean.getUid()));
        } else {
            vh.mVBlockGray.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            Glide.with(mContext).load(followBean.getAvatar()).apply(new RequestOptions().error(R.mipmap.default_head).centerCrop()).into(vh.mIvHead);
            // 没有字段判断
            vh.mIvExpertTag.setVisibility(View.GONE);
            vh.mTvIntro.setText(followBean.getIntro());
            vh.mTvName.setText(followBean.getNickName());

            setWatchView(vh, status, UserManager.getInstance().isMine(followBean.getUid()));
            vh.mTvFollow.setTag(position);
            vh.mTvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    FanEntity.FanBean bean = mItems.get(pos);
                    if (bean != null && mCallback != null) {
                        int followedUserUid = bean.getIFansId();
                        int status = bean.getMyAttentionStatus();
                        mCallback.onFollowClick(pos, followedUserUid, status);
                    }
                }
            });
        }

    }

    private void setWatchView(@NonNull FollowViewHolder vh, int status, boolean isMine) {
        vh.mTvFollow.setBackgroundResource(status == 1 ? R.drawable.shape_gray_has_follow_bg : R.drawable.shape_red_follow_bg);
        vh.mTvFollow.setTextColor(mContext.getResources().getColor(status == 1 ? R.color.colorText9 : R.color.white));
        if (isMine) {
            vh.mTvFollow.setText(status == 1 ? R.string.follow_each_other : R.string.follow_plus);
        } else
            vh.mTvFollow.setText(status == 1 ? R.string.follow_plus : R.string.follow_plus);

        if (status == 1 && isMine) {
            vh.mTvFollow.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_follow_each_other, 0, 0, 0);
            vh.mTvFollow.setCompoundDrawablePadding(TDevice.dip2px(3));
        } else {
            vh.mTvFollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            vh.mTvFollow.setCompoundDrawablePadding(0);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<FanEntity.FanBean> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }

    public final void removeItem(FanEntity.FanBean item) {
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
    public FanEntity.FanBean getItem(int position) {
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
        void onFollowClick(int pos, int userUid, int status);
    }
}
