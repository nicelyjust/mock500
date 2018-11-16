package com.yunzhou.tdinformation.lottery.history.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.ExpectPredictEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.view
 *  @文件名:   NextExpectView
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 14:17
 *  @描述：
 */

public interface NextExpectView extends BaseRefreshView {
    void showErrorView(int visible);

    void showEmptyView(int visible);

    void showLoadMoreData(List<ExpectPredictEntity> entities);

    void showDataView(List<ExpectPredictEntity> data);
}
