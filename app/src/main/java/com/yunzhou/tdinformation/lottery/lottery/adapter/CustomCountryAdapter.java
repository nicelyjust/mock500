package com.yunzhou.tdinformation.lottery.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.CustomLotteryViewHolder;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   CustomCountryAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 11:06
 *  @描述：
 */

public class CustomCountryAdapter extends BaseRvAdapter<RecommendLotteryEntity> {
    // 是否是已定制
    private boolean mTop;
    private Callback mCallback;

    public CustomCountryAdapter(boolean top, Context context, Callback callback) {
        super(context);
        mTop = top;
        this.mCallback = callback;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_custom_lottery, parent, false);
        return new CustomLotteryViewHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, RecommendLotteryEntity subscribeLotteryEntity, int position) {
        if (holder instanceof CustomLotteryViewHolder) {
            CustomLotteryViewHolder viewHolder = (CustomLotteryViewHolder) holder;
            viewHolder.mTvLotteryName.setText(subscribeLotteryEntity.getLotteryName());
            viewHolder.itemView.setTag(subscribeLotteryEntity);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TDevice.hasInternet()){
                        if (mCallback != null) {
                            mCallback.onItemClick(mTop ,(RecommendLotteryEntity)v.getTag());
                        }
                    } else {
                        ToastUtil.showShort(mContext ,R.string.no_net);
                    }
                }
            });
        }
    }
    public interface Callback{
        void onItemClick(boolean top, RecommendLotteryEntity item);
    }
}
