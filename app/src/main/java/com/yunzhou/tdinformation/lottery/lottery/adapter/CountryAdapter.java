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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.utils.Utils;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   CountryAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/17 16:12
 *  @描述：
 */

public class CountryAdapter extends BaseRvAdapter<LotteryEntity> {

    private Callback mCallback;

    public CountryAdapter(Context context, Callback callback) {
        super(context);
        this.mCallback = callback;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_lottery_result, parent, false);
        return new LotteryViewHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, LotteryEntity item, int position) {
        if (holder instanceof LotteryViewHolder) {
            LotteryViewHolder vh = (LotteryViewHolder) holder;
            Glide.with(mContext).load(item.getLotteryImg()).apply(new RequestOptions().placeholder(R.mipmap.default_lottery)
                    .error(R.mipmap.default_lottery).circleCrop()).into(vh.mIvLottery);

            vh.mTvLotteryName.setText(item.getLotteryName());
            vh.mTvNo.setText(item.getLotteryResultDto().getExpect());
            vh.mTvTime.setText(item.getLotteryResultDto().getOpenTime());
            vh.mTvWeek.setText(item.getLotteryResultDto().getWeekDay());
            setBall(vh.mLlBallContainer, item);
            setFunc(item, vh);
        }
    }

    private void setFunc(final LotteryEntity item, LotteryViewHolder vh) {
        List<LotteryEntity.LotteryFunctionListBean> functionList = item.getLotteryFunctionList();
        if (functionList == null || functionList.isEmpty()) {
            vh.mLlFuncContainer.setVisibility(View.GONE);
        } else {
            vh.mLlFuncContainer.setVisibility(View.VISIBLE);
            vh.mLlFuncContainer.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < functionList.size(); i++) {
                LotteryEntity.LotteryFunctionListBean functionListBean = functionList.get(i);
                TextView view = new TextView(mContext);
                view.setTextColor(Color.parseColor("#FF666666"));
                view.setText(functionListBean.getFunctionName());
                view.setBackgroundResource(R.drawable.selector_lottery_func);
                view.setGravity(Gravity.CENTER);
                view.setPadding(TDevice.dip2px(10) , TDevice.dip2px(5),TDevice.dip2px(10),TDevice.dip2px(5));
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
                vh.mLlFuncContainer.addView(view, params);
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
        } else if (size > 1){
            redCode = stringList.get(0);
            blueCode = stringList.get(1);
        }
        ballContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!TextUtils.isEmpty(redCode)) {
            String[] strings = redCode.split(",");
            for (int i = 0; i < strings.length; i++) {
                TextView view = new TextView(mContext);
                if (item.getAbbreviation().contains("sfc")){
                    view.setTextColor(Color.parseColor("#FF333333"));
                    view.setTextSize(18);
                } else{
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

    public @Nullable
    LotteryEntity getItem(int position) {
        LotteryEntity lotteryEntity;
        try {
            lotteryEntity = mItems.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lotteryEntity;
    }

    public interface Callback {
        void onFuncClick(String lotteryName, LotteryEntity.LotteryFunctionListBean item);

        void onItemClick(int pos);
    }
}
