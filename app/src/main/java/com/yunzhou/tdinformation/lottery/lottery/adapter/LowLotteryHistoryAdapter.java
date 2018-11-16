package com.yunzhou.tdinformation.lottery.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   LowLotteryHistoryAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 14:43
 *  @描述：
 */

public class LowLotteryHistoryAdapter extends BaseRvAdapter<LotteryEntity.LotteryResultDtoBean> {
    public LowLotteryHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_lottery_history, parent, false);
        return new LowLotteryHistoryVH(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, LotteryEntity.LotteryResultDtoBean item, int position) {
        if (holder instanceof LowLotteryHistoryVH) {
            LowLotteryHistoryVH vh = (LowLotteryHistoryVH) holder;
            vh.mVBlockGray.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            vh.mTvNo.setText(item.getExpect());
            vh.mTvTime.setText(item.getOpenTime());
            vh.mTvWeek.setText(item.getWeekDay());
            setBall(vh.mLlBallContainer ,item ,position);
        }
    }

    private void setBall(LinearLayout ballContainer, LotteryEntity.LotteryResultDtoBean item, int position) {
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
                    view.setTextColor(Color.parseColor("#FFDD262C"));
                    view.setTextSize(16);
                }
                view.setText(strings[i]);
                if (length <= 10 && position == 0) {
                    view.setTextSize(13);
                    view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setBackgroundResource(R.drawable.shape_red_ball);
                }
                view.setGravity(Gravity.CENTER);
                params.rightMargin = 0;
                if (i == 0)
                    params.leftMargin = 0;
                else
                    params.leftMargin = position == 0 ? TDevice.dip2px(15) : TDevice.dip2px(21);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
        if (!TextUtils.isEmpty(blueCode)) {
            String[] strings = blueCode.split(",");
            for (int i = 0; i < strings.length; i++) {
                TextView view = new TextView(mContext);
                view.setTextColor(Color.parseColor("#FF5893F4"));
                view.setText(strings[i]);
                view.setGravity(Gravity.CENTER);
                view.setTextSize(16);
                if (position == 0){
                    view.setTextSize(13);
                    view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setBackgroundResource(R.drawable.shape_blue_ball);
                }
                params.rightMargin = 0;
                params.leftMargin = position == 0 ? TDevice.dip2px(15) : TDevice.dip2px(21);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
    }

    public @Nullable LotteryEntity.LotteryResultDtoBean getItem(int position) {
        try {
            return mItems.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class LowLotteryHistoryVH extends RecyclerView.ViewHolder {
        @BindView(R.id.v_block_gray)
        View mVBlockGray;
        @BindView(R.id.tv_no_lottery_history)
        TextView mTvNo;
        @BindView(R.id.tv_time_lottery_history)
        TextView mTvTime;
        @BindView(R.id.tv_week_lottery_history)
        TextView mTvWeek;
        @BindView(R.id.ll_ball_container_lottery_history)
        LinearLayout mLlBallContainer;
        public LowLotteryHistoryVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
