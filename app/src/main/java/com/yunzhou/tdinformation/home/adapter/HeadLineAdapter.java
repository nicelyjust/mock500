package com.yunzhou.tdinformation.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.TimeUtils;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.home.BannerEntity;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.home.ExpertEntity;
import com.yunzhou.tdinformation.home.holder.CommonItemVH;
import com.yunzhou.tdinformation.home.holder.ExpertInfoHolder;
import com.yunzhou.tdinformation.home.holder.HeaderViewHolder;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.widget.MarqueeView;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.adapter
 *  @文件名:   HeadLineAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 15:02
 *  @描述：
 */

public class HeadLineAdapter extends RecyclerView.Adapter {
    private static final int ITEM_HEADER = 0x01;
    private static final int ITEM_EXPERT = 0x02;
    private static final int ITEM_INFO = 0x03;

    private Context mContext;
    private List<ContentEntity> mData;
    // banner轮播图地址集合
    private List<String> mUrls;

    private List<String> mAsList;
    private List<ExpertEntity> mExpertEntities;

    public HeadLineAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        mUrls = new ArrayList<>();
        mAsList = new ArrayList<>();
    }

    public void setData(List<ContentEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(2, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(2, mData.size());
        }
    }

    public void setBannerData(@NonNull BannerEntity bannerEntity) {
        List<BannerEntity.CarouselBean> carouselsList = bannerEntity.getCarouselsList();
        List<BannerEntity.AnnouncementBean> announcementList = bannerEntity.getAnnouncementList();
        for (BannerEntity.CarouselBean carouselBean : carouselsList) {
            mUrls.add(carouselBean.getOriginalImg());
        }
        for (BannerEntity.AnnouncementBean announcementBean : announcementList) {
            mAsList.add(announcementBean.getName());
        }
        notifyItemChanged(0);
    }
    public void setExpertData(List<ExpertEntity> expertEntities) {
        mExpertEntities = expertEntities;
        notifyItemChanged(1);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_header_headline, parent, false);
                return new HeaderViewHolder(view);
            case ITEM_EXPERT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_headline_spec, parent, false);
                return new ExpertInfoHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_headline_information, parent, false);
                return new CommonItemVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            setBannerView((HeaderViewHolder) holder);
        } else if (holder instanceof ExpertInfoHolder) {
            setExpertView((ExpertInfoHolder) holder);
        } else if (holder instanceof CommonItemVH) {
            setCommonInfo((CommonItemVH) holder, position - 2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_HEADER;
            case 1:
                return ITEM_EXPERT;
            default:
                return ITEM_INFO;
        }
    }

    @Override
    public int getItemCount() {
        return 2 + mData.size();
    }

    private void setCommonInfo(CommonItemVH holder, int pos) {
        ContentEntity content = mData.get(pos);
        holder.mTvTitle.setText(content.getTitle());
        if (content.getIsFreeType() == 0) {
            holder.mTvSubmitTime.setCompoundDrawablePadding(0);
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(0,0 ,0 ,0);
        } else {
            holder.mTvSubmitTime.setCompoundDrawablePadding(TDevice.dip2px(8));
            holder.mTvSubmitTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_need_pay ,0 ,0 ,0);
        }
        holder.mTvSubmitTime.setText(TimeUtils.getStandardDate(1000*content.getReleaseTime()));
        Glide.with(mContext).load(content.getTitleImg()).apply(RequestOptions.placeholderOf(R.mipmap.default_img)).into(holder.mIvImg);
        setItemClickListener(holder, pos);
    }

    private void setItemClickListener(CommonItemVH holder, int pos) {
        holder.itemView.setTag(pos);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentEntity contentEntity = mData.get((int) v.getTag());
                WebDetailActivity.start(mContext , 1, String.valueOf(contentEntity.getId()) , String.valueOf(UserManager.getInstance().getUid()));
            }
        });
    }

    private void setExpertView(ExpertInfoHolder holder) {
        ExpertInfoAdapter adapter = new ExpertInfoAdapter(mContext);
        RecyclerView rv = holder.mRvExpert;
        rv.setAdapter(adapter);
        adapter.setData(mExpertEntities);
        holder.mTvMoreExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setBannerView(HeaderViewHolder holder) {
        Banner banner = holder.mBanner;
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new BannerLoader());
        //设置图片集合
        banner.setImages(mUrls);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Stack);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        banner.setOffscreenPageLimit(mUrls.size());
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.LEFT);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // TODO: 2018/9/26
            }
        });
        banner.start();

        holder.mMarqueeView.startWithList(mAsList);
        holder.mMarqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                ToastUtil.showShort(mContext ,mAsList.get(position));
            }
        });
    }

    public void addAll(List<ContentEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            size += 2;
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    public void resume() {
    }

    public void pause() {

    }
}
