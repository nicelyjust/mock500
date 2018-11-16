package com.yunzhou.tdinformation.expert.view;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ArticleFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 18:48
 *  @描述：
 */

public class ExpertBlogFragment extends LazyBaseFragment2 {
    @Override
    protected void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    public static ExpertBlogFragment newInstance() {
        ExpertBlogFragment fragment = new ExpertBlogFragment();
        return fragment;
    }
}
