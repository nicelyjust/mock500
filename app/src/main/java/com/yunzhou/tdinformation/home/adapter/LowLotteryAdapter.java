package com.yunzhou.tdinformation.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.home.holder.CommonItemVH;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.adapter
 *  @文件名:   LayDetailAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 16:47
 *  @描述：
 */

public class LowLotteryAdapter extends RecyclerView.Adapter {
    private static final int ITEM_HEADER = 0x01;
    private static final int ITEM_COMMON = 0x02;

    private List<ContentEntity> mData;
    private Context mContext;
    private Callback mCallback;
    private List<LotteryChannelEntity> entities;
    private List<LotteryEntity.LotteryResultDtoBean> mLotteryResults;
    private int curPos;

    public LowLotteryAdapter(Context context, Callback callback) {
        this.mContext = context;
        mCallback = callback;
        this.mData = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.mLotteryResults = new ArrayList<>();
    }

    public void setData(List<ContentEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(1, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(1, mData.size());
        }
    }

    public void setLotteryColumnData(List<LotteryChannelEntity> entities) {
        this.entities.clear();
        this.entities.addAll(entities);
        mLotteryResults.clear();
        curPos = 0;
        notifyItemChanged(0);
    }

    public void setLotteryData(int pos, LotteryEntity.LotteryResultDtoBean resultDtoBean) {
        try {
            mLotteryResults.remove(pos);
        } catch (Exception e) {
            // 本来就没有
            e.printStackTrace();
        }
        mLotteryResults.add(pos, resultDtoBean);
        notifyItemChanged(0, "lottery");
    }

    public void addAll(List<ContentEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            size++;
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_header_low_lottery, parent, false);
                return new HeaderVH(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_headline_information, parent, false);
                return new CommonItemVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        if (holder instanceof HeaderVH) {
            setHeader((HeaderVH) holder, payloads.size() > 0);
        } else if (holder instanceof CommonItemVH) {
            setCommonInfo((CommonItemVH) holder, position - 1);
        }
    }

    private void setHeader(final HeaderVH holder, boolean payloads) {
        int size = entities.size();
        if (!payloads) {
            L.d("lz", "头部全部");
            setLotteryColumnView(holder, size);
            setBallAndNoView(holder);
        } else {
            L.d("lz", "只刷球的区域");
            setBallAndNoView(holder);
        }

    }

    private void setBallAndNoView(HeaderVH holder) {
        LotteryEntity.LotteryResultDtoBean lotteryResult = null;
        try {
            lotteryResult = mLotteryResults.get(curPos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lotteryResult != null) {
            holder.mTvRewardNo.setText(lotteryResult.getExpect() + "期");
            holder.mTvDate.setText(lotteryResult.getOpenTime());
            setBall(holder.mLlBallContainer, lotteryResult);
        }
    }

    private void setLotteryColumnView(HeaderVH holder, int size) {
        holder.mGroup.removeAllViews();
        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(TDevice.dip2px(40), TDevice.dip2px(82.5f));
        for (int i = 0; i < size; i++) {
            LotteryChannelEntity channelEntity = entities.get(i);
            final RadioButton radioButton = new RadioButton(mContext);
            lp.setMargins(0, 0, TDevice.dip2px(2), 0);
            radioButton.setBackgroundResource(R.drawable.selector_column_bg);
            radioButton.setButtonDrawable(null);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setText(channelEntity.getChannelName());
            radioButton.setTag(i);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    LotteryChannelEntity entity = entities.get(pos);
                    radioButton.setChecked(true);
                    curPos = pos;
                    try {
                        mLotteryResults.get(pos);
                        notifyItemChanged(0, "lottery");
                    } catch (Exception e) {
                        e.printStackTrace();
                        mCallback.getLotteryResult(pos ,entity.getChannelId());
                    }
                }
            });
            holder.mGroup.addView(radioButton);
        }
        if (entities.size() > 0) {
            RadioButton child = (RadioButton) holder.mGroup.getChildAt(curPos);
            child.setChecked(true);
        }
    }

    private void setCommonInfo(CommonItemVH holder, int pos) {
        ContentEntity content = mData.get(pos);
        holder.mTvTitle.setText(content.getTitle());
        if (content.getIsFreeType() == 0) {
            holder.mTvSubmitTime.setCompoundDrawablePadding(0);
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            holder.mTvSubmitTime.setCompoundDrawablePadding(TDevice.dip2px(8));
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_need_pay, 0, 0, 0);
        }
        holder.mTvSubmitTime.setText(TimeUtils.getStandardDate(1000 * content.getReleaseTime()));
        Glide.with(mContext).load(content.getTitleImg()).apply(RequestOptions.placeholderOf(R.mipmap.default_img)).into(holder.mIvImg);
        setItemClickListener(holder, pos);
    }

    private void setItemClickListener(CommonItemVH holder, int pos) {
        holder.itemView.setTag(pos);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentEntity contentEntity = mData.get((int) v.getTag());
                WebDetailActivity.start(mContext, 1, String.valueOf(contentEntity.getId()), String.valueOf(UserManager.getInstance().getUid()));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_HEADER;
            default:
                return ITEM_COMMON;
        }
    }

    private void setBall(LinearLayout ballContainer, LotteryEntity.LotteryResultDtoBean item) {
        String openCode = item.getOpenCode();
        List<String> stringList = Utils.splitOpenCode(openCode);
        int size = stringList.size();
        String redCode = null;
        String blueCode = null;
        if (size == 1) {
            redCode = stringList.get(0);
        } else if (size > 1) {
            redCode = stringList.get(0);
            blueCode = stringList.get(1);
        }
        ballContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!TextUtils.isEmpty(redCode)) {
            String[] strings = redCode.split(",");
            int length = strings.length;
            for (int i = 0; i < length; i++) {
                TextView view = new TextView(mContext);
                if (length >= 10) {
                    view.setTextColor(Color.parseColor("#FF333333"));
                    view.setTextSize(18);
                } else {
                    view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setTextSize(13);
                }
                view.setText(strings[i]);
                if (length <= 10)
                    view.setBackgroundResource(R.drawable.shape_red_big_ball);
                view.setGravity(Gravity.CENTER);
                params.rightMargin = 0;
                if (i == 0)
                    params.leftMargin = 0;
                else
                    params.leftMargin = TDevice.dip2px(6);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
        if (!TextUtils.isEmpty(blueCode)) {
            String[] strings = blueCode.split(",");
            for (int i = 0; i < strings.length; i++) {
                TextView view = new TextView(mContext);
                view.setTextColor(Color.parseColor("#FFFFFFFF"));
                view.setText(strings[i]);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.shape_blue_big_ball);
                params.rightMargin = 0;
                params.leftMargin = TDevice.dip2px(7);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1 + mData.size();
    }

    static class HeaderVH extends RecyclerView.ViewHolder {
        RadioGroup mGroup;
        TextView mTvDate;
        TextView mTvRewardNo;
        LinearLayout mLlBallContainer;

        public HeaderVH(View itemView) {
            super(itemView);
            mGroup = itemView.findViewById(R.id.rg_lottery);
            mTvDate = itemView.findViewById(R.id.tv_date_low_lottery);
            mTvRewardNo = itemView.findViewById(R.id.tv_reward_no);
            mLlBallContainer = itemView.findViewById(R.id.ll_ball_container);
        }
    }

    public interface Callback {
        void getLotteryResult(int pos, int channelId);
    }
}
