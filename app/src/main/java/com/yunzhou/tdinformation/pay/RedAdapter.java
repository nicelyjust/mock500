package com.yunzhou.tdinformation.pay;

import android.content.Context;
import android.support.annotation.IntDef;
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

public class RedAdapter extends RecyclerView.Adapter {

    private List<RedPacksBean> mItems;
    private Context mContext;
    private Callback mCallback;
    private int mFromWhere;
    public static final int TYPE_ODER = 0x01;
    public static final int TYPE_MINE = 0x02;

    @IntDef({TYPE_ODER, TYPE_MINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FromWhere {
    }

    public RedAdapter(Context context, Callback callback, @FromWhere int fromWhere) {
        this.mContext = context;
        this.mCallback = callback;
        mFromWhere = fromWhere;
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
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_red_packet_header, parent, false);
                return new RedHeaderVH(view);
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_red_packet_footer, parent, false);
                return new RedFooterVH(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_red_packet, parent, false);
                return new RedVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RedHeaderVH) {
            RedHeaderVH vh = (RedHeaderVH) holder;
            vh.mTvRedCount.setText("可用优惠券(" + mItems.size() + "张)");
        } else if (holder instanceof RedFooterVH) {
            RedFooterVH vh = (RedFooterVH) holder;
            vh.mTvCheckUselessRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onClickCheckUselessRed();
                    }
                }
            });
        } else if (holder instanceof RedVH){
            RedPacksBean packsBean = mItems.get(mFromWhere == TYPE_ODER ? position - 1 : position);
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
            vh.itemView.setTag(position - 1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null && mFromWhere == TYPE_ODER) {
                        vh.mIvSel.setVisibility(View.VISIBLE);
                        mCallback.onItemClick((int) v.getTag());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mFromWhere == TYPE_MINE) {
            if (position == mItems.size()) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (position == 0) {
                return 0;
            } else if (position == mItems.size() + 1) {
                return 2;
            } else {
                return 1;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mItems.size() == 0) {
            return 1;
        } else {
            if (mFromWhere == TYPE_MINE) {
                L.d("wtf" ,mItems.size() + 1);
                return mItems.size() + 1;
            } else {
                return 1 + mItems.size() + 1;
            }

        }
    }

    public interface Callback {
        void onClickCheckUselessRed();

        void onItemClick(int pos);
    }

    static class RedHeaderVH extends RecyclerView.ViewHolder {
        TextView mTvRedCount;

        public RedHeaderVH(View itemView) {
            super(itemView);
            mTvRedCount = itemView.findViewById(R.id.tv_red_count);
        }
    }

    static class RedFooterVH extends RecyclerView.ViewHolder {
        TextView mTvCheckUselessRed;

        public RedFooterVH(View itemView) {
            super(itemView);
            mTvCheckUselessRed = itemView.findViewById(R.id.tv_check_useless_red);
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
