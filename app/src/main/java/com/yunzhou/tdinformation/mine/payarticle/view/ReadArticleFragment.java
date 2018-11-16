package com.yunzhou.tdinformation.mine.payarticle.view;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.collect
 *  @文件名:   CollectArticleFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/3 13:52
 *  @描述：
 */

public class ReadArticleFragment extends LazyBaseFragment2 {
    @Override
    protected void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_common_refresh_list;
    }

    public static ReadArticleFragment newInstance() {
        return new ReadArticleFragment();
    }
}
