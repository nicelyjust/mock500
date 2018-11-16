package com.yunzhou.tdinformation.lottery.history.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.DiscussEntity;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.lottery.history.model.DiscussModel;
import com.yunzhou.tdinformation.lottery.history.model.IDiscussModel;
import com.yunzhou.tdinformation.lottery.history.view.DiscussView;
import com.yunzhou.tdinformation.utils.AppOperator;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.presenter
 *  @文件名:   DiscussPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 17:07
 *  @描述：
 */

public class DiscussPresenter extends WrapperPresenter<DiscussView> implements IDiscussModel {

    private static final String TAG = "DiscussPresenter";
    private final DiscussModel mModel;
    private volatile List<GeneralCommentBean> mRealCommentList;

    public int getTotal() {
        return mTotal;
    }

    private int mTotal;

    public DiscussPresenter() {
        mModel = new DiscussModel(this);
    }

    public void loadData(int loadType, int lotteryId, String expect) {
        if (mRealCommentList == null)
            mRealCommentList = new ArrayList<>();
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        mModel.loadData(loadType, lotteryId, expect);
    }

    @Override
    public void loadDataSuccess(final int loadType, final DiscussEntity content) {
        if (content.getGeneralCommentList() != null)
            mTotal = content.getGeneralCommentList().getTotal();
        AppOperator.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<GeneralCommentBean> newList = content.getGeneralCommentList().getList();
                switch (loadType) {
                    case AppConst.LOAD_TYPE_UP:
                        if (newList != null) {
                            int size = newList.size();
                            for (int i = 0; i < size; i++) {
                                GeneralCommentBean generalCommentBean = newList.get(i);
                                generalCommentBean.type = 0;
                                generalCommentBean.size = size;
                            }
                            if (!isViewNotNull()) {
                                return;
                            }
                            mView.finishLoadMore(0, true, newList.size() < DiscussModel.COUNT);
                            mView.hideLoading();
                            mView.showLoadMoreData(newList);
                            mRealCommentList.addAll(newList);
                        }
                        break;
                    default:
                        List<GeneralCommentBean> hotCommentList = content.getHotCommentList();
                        mRealCommentList.clear();
                        if (hotCommentList != null && !hotCommentList.isEmpty()) {
                            int size = hotCommentList.size();
                            for (int i = 0; i < size; i++) {
                                GeneralCommentBean generalCommentBean = hotCommentList.get(i);
                                generalCommentBean.type = 1;
                                generalCommentBean.size = size;
                            }
                            mRealCommentList.addAll(hotCommentList);
                        }
                        if (newList != null) {
                            int size = newList.size();
                            for (int i = 0; i < size; i++) {
                                GeneralCommentBean generalCommentBean = newList.get(i);
                                generalCommentBean.type = 0;
                                generalCommentBean.size = size;
                            }
                            mView.finishRefresh(true);
                            mRealCommentList.addAll(newList);
                        }
                        if (!isViewNotNull()) {
                            return;
                        }
                        if (mRealCommentList.size() == 0) {
                            mView.showEmptyView(View.VISIBLE);
                        }
                        mView.hideLoading();
                        mView.showDataView(mRealCommentList);
                        break;
                }
            }
        });
    }

    @Override
    public void loadDataError(int loadType, String message) {
        Log.e(TAG, "loadDataError: " + message);
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                mView.finishLoadMore(0, false, true);
                break;
            case AppConst.LOAD_TYPE_DOWN:
                mView.finishRefresh(false);
                if (mRealCommentList.isEmpty())
                    mView.showErrorView(View.VISIBLE);
                break;
            default:
                mView.showErrorView(View.VISIBLE);
                break;
        }
    }

    @Override
    public void insertLikeOk(int isPraise) {
        if (isViewNotNull()) {
            mView.showToast(isPraise == 0 ? "点赞成功" : "取消点赞成功");
        }
    }

    @Override
    public void insertLikeError(int isPraise, String message) {
        Log.e(TAG, "insertLikeError: " + message);
        if (isViewNotNull()) {
            mView.showToast(isPraise == 0 ? "点赞失败" : "取消点赞失败");
        }
    }

    @Override
    public void insertComment(GeneralCommentBean bean) {
        if (!isViewNotNull()) {
            return;
        }
        if (bean == null) {
            mView.showInsertCommentError();
        } else {
            mView.showInsertComment(bean);
        }
    }

    public void insertLike(Context context, GeneralCommentBean commentBean) {
        mModel.insertLike(context, commentBean);
    }

    public void comment(Context context, String content, int lotteryId, String expect) {
        mModel.comment(context, content, lotteryId, expect);
    }

    @Override
    public void destroy() {
        mRealCommentList.clear();
        mRealCommentList = null;
    }
}
