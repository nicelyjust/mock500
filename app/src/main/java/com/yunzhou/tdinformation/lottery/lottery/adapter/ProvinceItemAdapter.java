package com.yunzhou.tdinformation.lottery.lottery.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.ProvinceItem;
import com.yunzhou.tdinformation.utils.Utils;

import java.util.List;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   ProvinceViewHolder
 *  @创建者:   lz
 *  @创建时间:  2018/10/23 16:12
 *  @描述：
 */
public class ProvinceItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ProvinceItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_DETAIL = 1;
    private List<MultiItemEntity> mData;
    private final CountryAdapter.Callback mCallback;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ProvinceItemAdapter(List<MultiItemEntity> data, CountryAdapter.Callback callback) {
        super(data);
        mData = data;
        mCallback = callback;
        addItemType(TYPE_LEVEL_0, R.layout.list_item_province);
        addItemType(TYPE_DETAIL, R.layout.item_lottery_result);
    }

    @NonNull
    @Override
    public List<MultiItemEntity> getData() {
        return mData;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ProvinceItem lv0 = (ProvinceItem) item;
                holder.setText(R.id.tv_province_name, lv0.province)
                        .setImageResource(R.id.iv_province_arrow, lv0.isExpanded() ? R.mipmap.spinner_back : R.mipmap.spinner_back);
                holder.getView(R.id.tv_type_title).setVisibility(lv0.pos == 0 ? View.VISIBLE : View.GONE);
                holder.getView(R.id.ll_province_area).setOnClickListener(new View.OnClickListener() {
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
                final LotteryEntity entity = (LotteryEntity) item;
                Glide.with(mContext).load(entity.getLotteryImg()).apply(new RequestOptions().placeholder(R.mipmap.default_lottery)
                        .error(R.mipmap.default_lottery).circleCrop()).into((ImageView) holder.getView(R.id.iv_lottery));
                holder.setText(R.id.tv_lottery_name, entity.getLotteryName());
                holder.setText(R.id.tv_no_lottery, entity.getLotteryResultDto().getExpect());
                holder.setText(R.id.tv_time_lottery, entity.getLotteryResultDto().getOpenTime());
                holder.setText(R.id.tv_week_lottery, entity.getLotteryResultDto().getWeekDay());
                setBall((LinearLayout) holder.getView(R.id.ll_ball_container_lottery), entity);
                setFunc(entity, (LinearLayout) holder.getView(R.id.ll_func_container_lottery));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        mCallback.onItemClick(pos);
                    }
                });
                break;
        }

    }

    private void setFunc(final LotteryEntity item, LinearLayout llFuncContainer) {

        List<LotteryEntity.LotteryFunctionListBean> functionList = item.getLotteryFunctionList();
        if (functionList == null || functionList.isEmpty()) {
            llFuncContainer.setVisibility(View.GONE);
        } else {
            llFuncContainer.setVisibility(View.VISIBLE);
            llFuncContainer.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < functionList.size(); i++) {
                LotteryEntity.LotteryFunctionListBean functionListBean = functionList.get(i);
                TextView view = new TextView(mContext);
                view.setTextColor(Color.parseColor("#FF666666"));
                view.setText(functionListBean.getFunctionName());
                view.setBackgroundResource(R.drawable.selector_lottery_func);
                view.setGravity(Gravity.CENTER);
                view.setPadding(TDevice.dip2px(10), TDevice.dip2px(5), TDevice.dip2px(10), TDevice.dip2px(5));
                if (i != functionList.size())
                    params.rightMargin = TDevice.dip2px(25);
                else
                    params.rightMargin = 0;
                view.setTag(functionListBean);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LotteryEntity.LotteryFunctionListBean bean = (LotteryEntity.LotteryFunctionListBean) v.getTag();
                        if (mCallback != null)
                            mCallback.onFuncClick(item.getLotteryName(), bean);
                    }
                });
                llFuncContainer.addView(view, params);
            }

        }
    }

    private void setBall(LinearLayout ballContainer, LotteryEntity item) {
        String openCode = item.getLotteryResultDto().getOpenCode();
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
            for (int i = 0; i < strings.length; i++) {
                TextView view = new TextView(mContext);
                if (item.getAbbreviation().contains("sfc")) {
                    view.setTextColor(Color.parseColor("#FF333333"));
                    view.setTextSize(18);
                } else {
                    view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setTextSize(13);
                }
                view.setText(strings[i]);
                if (!item.getAbbreviation().contains("sfc"))
                    view.setBackgroundResource(R.drawable.shape_red_ball);
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
                view.setBackgroundResource(R.drawable.shape_blue_ball);
                params.rightMargin = 0;
                params.leftMargin = TDevice.dip2px(7);
                params.bottomMargin = 4;
                ballContainer.addView(view, params);
            }
        }
    }
}
