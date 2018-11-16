package com.yunzhou.tdinformation.lottery.lottery.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.ProvinceLotteryItem;

import java.util.List;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   LotteryCategoryAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/23 16:12
 *  @描述：
 */
public class LotteryCategoryAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = LotteryCategoryAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_DETAIL = 1;
    private Callback mCallback;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LotteryCategoryAdapter(List<MultiItemEntity> data ,Callback callback) {
        super(data);
        this.mCallback = callback;
        addItemType(TYPE_LEVEL_0, R.layout.item_custom_lottery);
        addItemType(TYPE_DETAIL, R.layout.item_custom_lottery2);
    }
    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ProvinceLotteryItem lv0 = (ProvinceLotteryItem) item;
                holder.setText(R.id.tv_lottery_name, lv0.province);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos ,true);
                        } else {
                            expand(pos ,true);
                        }
                    }
                });
                break;
            case TYPE_DETAIL:
                final RecommendLotteryEntity entity = (RecommendLotteryEntity) item;
                holder.setText(R.id.tv_lottery_name, entity.getLotteryName());
                holder.itemView.setTag(item);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MultiItemEntity item = (MultiItemEntity) view.getTag();
                        if (TDevice.hasInternet()){
                            int pos = holder.getAdapterPosition();
                            remove(pos);
                            if (mCallback != null)
                                mCallback.onItemClick(pos,item , entity);
                        } else {
                            ToastUtil.showShort(mContext ,R.string.no_net);
                        }

                    }
                });
                break;
        }

    }

    /**
     * 增加子项目
     * @param item
     * @param parentPos
     */
    public void addSubItem(RecommendLotteryEntity item, int parentPos) {
        ProvinceLotteryItem lotteryItem = (ProvinceLotteryItem) getData().get(parentPos);
        lotteryItem.addSubItem(item);
        collapse(parentPos);
        notifyDataSetChanged();
    }
    public interface Callback{
        void onItemClick(int adapterPosition, MultiItemEntity item, RecommendLotteryEntity entity);
    }
}
