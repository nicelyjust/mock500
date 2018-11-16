package com.yunzhou.tdinformation.lottery.history.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.adapter
 *  @文件名:   NextPredictAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:54
 *  @描述：
 */

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.ExpectPredictEntity;

import java.util.List;

public class NextPredictAdapter extends BaseQuickAdapter<ExpectPredictEntity, BaseViewHolder> {

    public NextPredictAdapter(@Nullable List<ExpectPredictEntity> data) {
        super(R.layout.item_next_predict, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExpectPredictEntity item) {
        holder.setText(R.id.tv_name_predict, item.getContentTitle());
        holder.setVisible(R.id.tv_recommend_predict, item.getIsRecommend() == 1);
    }
}
