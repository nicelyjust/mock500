package com.yunzhou.tdinformation.home.adapter;
/*
 *  @项目名：  gank-io
 *  @包名：    com.eebbk.geek.practice.adapter
 *  @创建者:   lz
 *  @创建时间:  2018/7/8 17:01
 *  @修改时间:  nicely 2018/7/8 17:01
 *  @描述：    TODO
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BannerPagerAdapter extends PagerAdapter{
    private Context mContext;
    private int[] mArrUrl;

    public BannerPagerAdapter(Context context) {
        mContext = context;
    }
    @Override
    public int getCount() {
        return mArrUrl == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        position = position % mArrUrl.length;
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(mArrUrl[position]).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
