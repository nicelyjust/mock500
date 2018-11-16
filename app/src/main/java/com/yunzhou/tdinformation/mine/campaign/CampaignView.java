package com.yunzhou.tdinformation.mine.campaign;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.ActivityEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.campaign
 *  @文件名:   CampaignView
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 12:54
 *  @描述：
 */

public interface CampaignView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    void showLoadMoreData(List<ActivityEntity.ActivityBean> list);

    void showDataView(List<ActivityEntity.ActivityBean> data);
}
