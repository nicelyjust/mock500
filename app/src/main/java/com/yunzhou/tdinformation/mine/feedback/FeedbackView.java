package com.yunzhou.tdinformation.mine.feedback;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.HelpEntity;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.feedback
 *  @文件名:   FeedbackView
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 18:59
 *  @描述：
 */

public interface FeedbackView extends BaseView {
    void showLoadMoreData(List<HelpEntity> list);

    void showDataView(List<HelpEntity> data);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);
}
