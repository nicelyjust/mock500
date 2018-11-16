package com.yunzhou.tdinformation.mine.follow;

import com.yunzhou.tdinformation.base.mvp.BaseRefreshView;
import com.yunzhou.tdinformation.bean.FollowEntity;

import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.follow
 *  @文件名:   MineFollowView
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 9:27
 *  @描述：
 */

public interface MineFollowView extends BaseRefreshView {
    void showLoadMoreData(List<FollowEntity.FollowBean> list);

    void showDataView(List<FollowEntity.FollowBean> data);

    void showEmptyView(int visibility);

    void showErrorView(int visibility);

    /**
     *
     * @param msg 提示消息
     * @param pos 位置
     */
    void showFollowSuccess(String msg, int pos);

    void showFollowError( String message);
}
