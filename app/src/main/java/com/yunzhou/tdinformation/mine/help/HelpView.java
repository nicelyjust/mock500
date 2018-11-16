package com.yunzhou.tdinformation.mine.help;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.HelpEntity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.help
 *  @文件名:   HelpView
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 15:39
 *  @描述：
 */

public interface HelpView extends BaseView {
    void showLoadMoreData(List<HelpEntity> list);

    void showDataView(List<HelpEntity> data);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);
}
