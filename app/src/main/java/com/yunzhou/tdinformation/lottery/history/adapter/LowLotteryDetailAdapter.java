package com.yunzhou.tdinformation.lottery.history.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.adapter
 *  @文件名:   LowLotteryDetailAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 10:22
 *  @描述：
 */

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.LotteryDetailEntity;

import java.util.List;

public class LowLotteryDetailAdapter extends BaseQuickAdapter<LotteryDetailEntity.BonusSituationDtoListBean, BaseViewHolder> {

    public LowLotteryDetailAdapter(int layoutResId, @Nullable List<LotteryDetailEntity.BonusSituationDtoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, LotteryDetailEntity.BonusSituationDtoListBean item) {
        holder.setText(R.id.tv_lottery_detail_1, item.getPrize());
        holder.setText(R.id.tv_lottery_detail_2, item.getWinningConditions());
        holder.setText(R.id.tv_lottery_detail_3, item.getNumberOfWinners());
        holder.setText(R.id.tv_lottery_detail_4, item.getSingleNoteBonus());
        if (item.getIsSuperAddition() == 1) {
            holder.setVisible(R.id.ll_plus_area, true);
            holder.setVisible(R.id.v_plus, true);
            holder.setText(R.id.tv_lottery_detail_plus_3, item.getAdditionNumber());
            holder.setText(R.id.tv_lottery_detail_plus_4, item.getAdditionBonus());
        } else {
            holder.setGone(R.id.ll_plus_area, false);
            holder.setGone(R.id.v_plus, false);
        }
    }
}
