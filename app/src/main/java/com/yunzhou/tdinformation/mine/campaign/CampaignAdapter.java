package com.yunzhou.tdinformation.mine.campaign;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.ActivityEntity;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.campaign
 *  @文件名:   CampaignAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 14:04
 *  @描述：
 */

public class CampaignAdapter extends BaseRvAdapter<ActivityEntity.ActivityBean> {

    public CampaignAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_campaign, parent, false);
        return new CampaignViewHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, ActivityEntity.ActivityBean activityBean, int position) {
        if (holder instanceof CampaignViewHolder) {
            CampaignViewHolder vh = (CampaignViewHolder) holder;
            vh.mVBlockGray.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            vh.mTvCampaignName.setText(activityBean.getName());
            vh.mTvCampaignDesc.setText(activityBean.getDesc());
            vh.mTvCampaignDeadline.setText(activityBean.getActivityStart() + "-" + activityBean.getActivityEnd());
            Glide.with(mContext).load(activityBean.getSmallImg()).apply(new RequestOptions().error(R.mipmap.default_img)).into(vh.mIvCampaign);
        }
    }

    @Nullable
    public ActivityEntity.ActivityBean getItem(int position) {
        return mItems.get(position);
    }

    static class CampaignViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_block_gray)
        View mVBlockGray;
        @BindView(R.id.iv_campaign)
        ImageView mIvCampaign;
        @BindView(R.id.tv_campaign_name)
        TextView mTvCampaignName;
        @BindView(R.id.tv_campaign_deadline)
        TextView mTvCampaignDeadline;
        @BindView(R.id.tv_campaign_desc)
        TextView mTvCampaignDesc;

        public CampaignViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
