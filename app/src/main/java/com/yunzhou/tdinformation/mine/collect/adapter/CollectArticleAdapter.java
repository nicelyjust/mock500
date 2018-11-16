package com.yunzhou.tdinformation.mine.collect.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.CollectArticleEntity;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect.adapter
 *  @文件名:   CollectArticleAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 23:55
 *  @描述：
 */

public class CollectArticleAdapter extends BaseQuickAdapter<CollectArticleEntity.CollectBean, BaseViewHolder> {

    @BindView(R.id.iv_headline_img)
    ImageView mIvHeadlineImg;
    @BindView(R.id.tv_headline_title)
    TextView mTvHeadlineTitle;
    @BindView(R.id.tv_submit_time)
    TextView mTvSubmitTime;

    public CollectArticleAdapter(@Nullable List<CollectArticleEntity.CollectBean> data) {
        super(R.layout.item_headline_information, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CollectArticleEntity.CollectBean item) {
        holder.setText(R.id.tv_headline_title, item.getTitle());
        ImageView imageView = holder.getView(R.id.iv_headline_img);
        TextView textView = holder.getView(R.id.tv_submit_time);
        textView.setCompoundDrawablePadding(0);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        textView.setText(TimeUtils.getStandardDate(TimeUtils.getTime(item.getReleaseTime())));
        Glide.with(mContext).load(item.getTitleImg()).apply(RequestOptions.placeholderOf(R.mipmap.default_img).error(R.mipmap.default_img).centerCrop()).into(imageView);
    }
}
