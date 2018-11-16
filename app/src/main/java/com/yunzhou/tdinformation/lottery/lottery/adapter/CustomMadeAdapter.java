package com.yunzhou.tdinformation.lottery.lottery.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   CustomMadeAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/25 10:18
 *  @描述：
 */

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.SubscribeLotteryEntity;
import com.yunzhou.tdinformation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomMadeAdapter extends RecyclerView.Adapter {
    private static final String TAG = "CustomMadeAdapter";
    private static final int ITEM_HEADER = 0x1;
    private static final int ITEM_EMPTY = 0x2;
    private static final int ITEM_SUBSCRIBE = 0x3;
    private static final int ITEM_RECOMMEND = 0x4;

    private List<RecommendLotteryEntity> mRecommendData;
    private List<SubscribeLotteryEntity> mSubscribeData;
    private Context mContext;
    private Callback mCallback;

    public CustomMadeAdapter(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mRecommendData = new ArrayList<>();
        this.mSubscribeData = new ArrayList<>();
    }

    public void setRecommendData(@NonNull List<RecommendLotteryEntity> recommendData) {
        mRecommendData.clear();
        mRecommendData.addAll(recommendData);
        notifyDataSetChanged();
    }

    public void setSubscribeData(@NonNull List<SubscribeLotteryEntity> subscribeData) {
        mSubscribeData.clear();
        mSubscribeData.addAll(subscribeData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_header, parent, false);
                return new HeaderVH(view);
            case ITEM_EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_empty, parent, false);
                return new EmptyVH(view);
            case ITEM_SUBSCRIBE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottery_result, parent, false);
                return new LotteryViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_lottery, parent, false);
                return new RecommendVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderVH) {
            HeaderVH vh = (HeaderVH) holder;
            String source = "+ 定制更多彩种 (最多十个)";
            SpannableString spannableString = new SpannableString(source);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF999999"));
            spannableString.setSpan(colorSpan, 9, source.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(10, true);
            spannableString.setSpan(sizeSpan, 9, source.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            vh.mBtnSelectMore.setText(spannableString);
            vh.mBtnSelectMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onSubscribeMoreClick();
                    }
                }
            });
        } else if (holder instanceof LotteryViewHolder) {
            LotteryViewHolder vh = (LotteryViewHolder) holder;
            LotteryEntity item = mSubscribeData.get(position - 1).getLotteryDto();
            Glide.with(vh.mIvLottery).load(item.getLotteryImg()).apply(new RequestOptions().placeholder(R.mipmap.default_lottery)
                    .error(R.mipmap.default_lottery).circleCrop()).into(vh.mIvLottery);
            vh.itemView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            vh.mTvLotteryName.setText(item.getLotteryName());
            vh.mTvNo.setText(item.getLotteryResultDto().getExpect());
            vh.mTvTime.setText(item.getLotteryResultDto().getOpenTime());
            vh.mTvWeek.setText(item.getLotteryResultDto().getWeekDay());
            setBall(vh.mLlBallContainer, item);
            setFunc(item, vh);
            vh.itemView.setTag(position - 1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onItemClick((int) v.getTag());
                    }
                }
            });
        } else if (holder instanceof RecommendVH) {
            RecommendVH vh = (RecommendVH) holder;
            int index;
            if (mSubscribeData.size() == 0) {
                index = position - 2;
            } else {
                index = position - 1 - mSubscribeData.size();
            }
            RecommendLotteryEntity item = mRecommendData.get(index);
            vh.mTvTitle.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
            vh.mTvLotteryName.setText(item.getLotteryName());
            vh.mBtnSelect.setTag(item);
            vh.mBtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecommendLotteryEntity item = (RecommendLotteryEntity) v.getTag();
                    if (TDevice.hasInternet()){
                        if (mCallback != null) {
                            mCallback.onSubscribeClick(item);
                        }
                    } else {
                        ToastUtil.showShort(mContext ,R.string.no_net);
                    }

                }
            });
        } else {
            // 空视图 bind data
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
                            mCallback.onFuncClick(item.getLotteryName() ,bean);
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

    @Override
    public int getItemCount() {
        if (mSubscribeData.size() == 0) {
            return 1 + 1 + mSubscribeData.size() + mRecommendData.size();
        } else {
            return 1 + mSubscribeData.size() + mRecommendData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int subscribeSize = mSubscribeData.size();
        if (position == 0) {
            L.d(TAG, "header");
            return ITEM_HEADER;
        } else if (subscribeSize == 0 && position == 1) {
            L.d(TAG, "empty");
            return ITEM_EMPTY;
        } else if (subscribeSize > 0 && position < subscribeSize + 1) {
            L.d(TAG, "subscribe");
            return ITEM_SUBSCRIBE;
        } else {
            L.d(TAG, "recommend");
            return ITEM_RECOMMEND;
        }
    }

    public LotteryEntity getSubscribeItem(int position) {
        try {
            return mSubscribeData.get(position).getLotteryDto();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clearSubscribeData() {
        mSubscribeData.clear();
        notifyDataSetChanged();
    }

    public void clearRecommendData() {
        mRecommendData.clear();
        notifyDataSetChanged();
    }

    static class HeaderVH extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_select_more_lottery)
        Button mBtnSelectMore;

        public HeaderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyVH extends RecyclerView.ViewHolder {

        public EmptyVH(View itemView) {
            super(itemView);
        }
    }

    static class RecommendVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recommend_title)
        TextView mTvTitle;
        @BindView(R.id.btn_select_lottery)
        Button mBtnSelect;
        @BindView(R.id.tv_lottery_name)
        TextView mTvLotteryName;

        public RecommendVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {

        void onSubscribeClick(RecommendLotteryEntity item);

        void onSubscribeMoreClick();

        void onFuncClick(String lotteryName, LotteryEntity.LotteryFunctionListBean item);

        void onItemClick(int position);
    }
}
