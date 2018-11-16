package com.yunzhou.tdinformation.lottery.history.view;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.view
 *  @文件名:   DiscussView
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 17:07
 *  @描述：
 */

public interface DiscussView extends BaseRefreshView {
    void showEmptyView(int visibility);

    void showErrorView(int visibility);
    // 子线程
    void showLoadMoreData(List<GeneralCommentBean> newList);
    // 子线程
    void showDataView(List<GeneralCommentBean> realCommentList);

    void showToast(String msg);

    void showInsertComment(GeneralCommentBean bean);

    void showInsertCommentError();

}
