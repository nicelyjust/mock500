package com.yunzhou.tdinformation.lottery.history.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.model
 *  @文件名:   IDiscussModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 17:12
 *  @描述：
 */

import com.yunzhou.tdinformation.bean.lottery.DiscussEntity;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;

public interface IDiscussModel {
    void loadDataSuccess(int loadType, DiscussEntity content);

    void loadDataError(int loadType, String message);

    void insertLikeOk(int isPraise);

    void insertLikeError(int isPraise, String message);

    void insertComment(GeneralCommentBean bean);
}
