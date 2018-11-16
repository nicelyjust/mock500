package com.yunzhou.tdinformation.home.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.adapter
 *  @文件名:   BannerLoader
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 18:35
 *  @描述：
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;
import com.yunzhou.tdinformation.R;

public class BannerLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).apply(RequestOptions.placeholderOf(R.mipmap.default_img)).into(imageView);
    }
}
