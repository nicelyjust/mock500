package com.yunzhou.tdinformation.mine.red.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   MyRedPacketView
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 10:20
 *  @描述：
 */

public interface MyRedPacketView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showDataView(List<RedPacksBean> data);

    void showLoadMoreData(List<RedPacksBean> list);
}
