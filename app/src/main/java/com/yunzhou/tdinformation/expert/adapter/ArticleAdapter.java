package com.yunzhou.tdinformation.expert.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.expert.ArticleEntity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert.adapter
 *  @文件名:   ArticleAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:10
 *  @描述：
 */

public class ArticleAdapter extends BaseRvAdapter<ArticleEntity.ArticleBean> {

    private String mHeadUrl;

    public ArticleAdapter(Context context, String headUrl) {
        super(context);
        mHeadUrl = headUrl;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, ArticleEntity.ArticleBean articleBean, int position) {
        if (holder instanceof ArticleHolder) {
            ArticleHolder vh = (ArticleHolder) holder;
            vh.mTvArticleDetail.setText(articleBean.getTitle());
            vh.mTvPayTag.setVisibility(articleBean.getIsFreeType() == 1 ? View.VISIBLE : View.GONE);
            vh.mTvViewCount.setText(String.valueOf(articleBean.getViewCount()));
            vh.mTvTime.setText(TimeUtils.getStandardDate(articleBean.getReleaseTime() * 1000));
            if (!TextUtils.isEmpty(mHeadUrl)) {
                Glide.with(mContext).load(mHeadUrl).apply(new RequestOptions().circleCrop()).into(vh.mIvArticleAuthor);
            }
            vh.mTvAuthorName.setText(articleBean.getAuthor());
        }
    }

    public @Nullable ArticleEntity.ArticleBean getItem(int position) {
        ArticleEntity.ArticleBean articleBean;
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
