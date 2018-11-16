package com.yunzhou.tdinformation.mine.payarticle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.PayArticleEntity;
import com.yunzhou.tdinformation.mine.payarticle.RoundBackgroundColorSpan;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert.adapter
 *  @文件名:   ArticleAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:10
 *  @描述：
 */

public class PayArticleAdapter extends BaseRvAdapter<PayArticleEntity.ArticlesBean> {

    public PayArticleAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, PayArticleEntity.ArticlesBean articleBean, int position) {
        if (holder instanceof ArticleHolder) {
            ArticleHolder vh = (ArticleHolder) holder;
            String title = articleBean.getTitle();
            String lotteryName = articleBean.getLotteryName();
            if (TextUtils.isEmpty(lotteryName)) {
                vh.mTvArticleDetail.setText(title);
            } else {
                SpannableString spannableString = new SpannableString(lotteryName.concat("  " + title));
                AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(10, true);
                spannableString.setSpan(sizeSpan, 0, lotteryName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(Color.parseColor("#E2EDF8"), Color.parseColor("#4389C4"));
                spannableString.setSpan(span, 0, lotteryName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                vh.mTvArticleDetail.setText(spannableString);
            }
            if (articleBean.getCanBet() == 1) {
                vh.mTvPayTag.setText("可投注");
                vh.mTvPayTag.setBackgroundResource(R.drawable.shape_red_comment_bg);
                vh.mTvPayTag.setTextColor(mContext.getResources().getColor(R.color.white));
            } else if (articleBean.getCanBuy() == 1) {
                vh.mTvPayTag.setText("热售中");
                vh.mTvPayTag.setBackgroundResource(R.drawable.shape_red_comment_bg);
                vh.mTvPayTag.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                if (articleBean.getOpened() == 1) {
                    vh.mTvPayTag.setText("已公开");
                    vh.mTvPayTag.setBackgroundResource(R.drawable.shape_gray_stroke_min_radius_bg);
                    vh.mTvPayTag.setTextColor(mContext.getResources().getColor(R.color.colorText9));
                } else {
                    vh.mTvPayTag.setText("待开奖");
                    vh.mTvPayTag.setBackgroundResource(R.drawable.shape_red_stroke_min_radius_bg);
                    vh.mTvPayTag.setTextColor(mContext.getResources().getColor(R.color.colorText9));
                }
            }

            vh.mTvViewCount.setText(String.valueOf(articleBean.getViewCount()));
            vh.mTvTime.setText(TimeUtils.getStandardDate(articleBean.getReleaseTime() * 1000));
            if (!TextUtils.isEmpty(articleBean.getAvatar())) {
                Glide.with(mContext).load(articleBean.getAvatar()).apply(new RequestOptions().placeholder(R.mipmap.default_head).error(R.mipmap.default_head).circleCrop()).into(vh.mIvArticleAuthor);
            }
            vh.mTvAuthorName.setText(articleBean.getNickName());
        }
    }

    @Nullable
    public PayArticleEntity.ArticlesBean getItem(int position) {
        PayArticleEntity.ArticlesBean articleBean;
        try {
            articleBean = mItems.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return articleBean;
    }

    static class ArticleHolder extends RecyclerView.ViewHolder {
        TextView mTvPayTag;
        TextView mTvArticleDetail;
        TextView mTvTime;
        TextView mTvViewCount;
        ImageView mIvArticleAuthor;
        TextView mTvAuthorName;

        public ArticleHolder(View itemView) {
            super(itemView);
            mTvPayTag = itemView.findViewById(R.id.tv_pay_tag);
            mTvArticleDetail = itemView.findViewById(R.id.tv_article_detail);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvViewCount = itemView.findViewById(R.id.tv_view_count);
            mIvArticleAuthor = itemView.findViewById(R.id.iv_article_author);
            mTvAuthorName = itemView.findViewById(R.id.tv_author_name);
        }
    }
}
