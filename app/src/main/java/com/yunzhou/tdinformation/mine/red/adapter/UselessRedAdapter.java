package com.yunzhou.tdinformation.mine.red.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay
 *  @文件名:   RedAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/18 18:21
 *  @描述：
 */

public class UselessRedAdapter extends RecyclerView.Adapter {

    private List<RedPacksBean> mItems;
    private Context mContext;
    private static final int ITEM_COMMON = 0x01;
    private static final int ITEM_FOOTER = 0x02;


    public UselessRedAdapter(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    public void setData(List<RedPacksBean> items) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_red_packet_no_more_footer, parent, false);
                return new RedFooterVH(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_red_packet, parent, false);
                return new RedVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RedVH) {
            RedPacksBean packsBean = mItems.get(position);
            final RedVH vh = (RedVH) holder;
            boolean inUse = !packsBean.isExpire;
            vh.mIvExpire.setVisibility(inUse ? View.GONE : View.VISIBLE);
            vh.mTvDeadline.setText(packsBean.getExpireDate());
            vh.mTvMuch.setText(String.valueOf(packsBean.getAmount()));
            vh.mTvMuch.setEnabled(inUse);
            vh.mTvRedNum.setText(packsBean.getAmount() + "彩币");
            vh.mTvRedNum.setEnabled(inUse);
            vh.mTvRedName.setText(packsBean.getName());
            vh.mTvRedName.setEnabled(inUse);
            vh.mTvUseCondition.setText("满" + packsBean.getNeedAmount() + "彩币可用");
            vh.mTvRule.setText(packsBean.getComment());
            vh.mIvRedPacket.setImageResource(inUse ? R.mipmap.red_packet : R.mipmap.gray_red_packet);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return ITEM_FOOTER;
        } else {
            return ITEM_COMMON;
        }
    }

    @Override
    public int getItemCount() {
        if (mItems.size() == 0) {
            return 0;
        } else {
            L.d("wtf", mItems.size() + 1);
            return mItems.size() + 1;

        }
    }

    static class RedFooterVH extends RecyclerView.ViewHolder {
        public RedFooterVH(View itemView) {
            super(itemView);
        }
    }

    static class RedVH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_red_packet)
        ImageView mIvRedPacket;
        @BindView(R.id.iv_sel)
        ImageView mIvSel;
        @BindView(R.id.tv_much)
        TextView mTvMuch;
        @BindView(R.id.tv_red_name)
        TextView mTvRedName;
        @BindView(R.id.tv_red_num)
        TextView mTvRedNum;
        @BindView(R.id.tv_deadline)
        TextView mTvDeadline;
        @BindView(R.id.tv_use_condition)
        TextView mTvUseCondition;
        @BindView(R.id.tv_rule)
        TextView mTvRule;
        @BindView(R.id.iv_guo_qi)
        ImageView mIvExpire;

        public RedVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
