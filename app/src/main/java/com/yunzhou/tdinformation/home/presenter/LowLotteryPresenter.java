package com.yunzhou.tdinformation.home.presenter;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.home.ContentEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryChannelEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.home.model.LowLotteryModel;
import com.yunzhou.tdinformation.home.view.LowLotteryView;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.home.presenter
 *  @文件名:   LowLotteryPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 16:42
 *  @描述：
 */

public class LowLotteryPresenter extends WrapperPresenter<LowLotteryView> implements ILowLotteryPresenter{

    private final LowLotteryModel mModel;
    private List<ContentEntity> mContents;
    public LowLotteryPresenter() {
        mModel = new LowLotteryModel(this ,1);
        mContents = new ArrayList<>();
    }

    @Override
    public void destroy() {

    }

    public void loadChannelColumn() {
        mModel.loadChannelColumn(1);
    }
    public void loadChannelLottery(int pos ,int channelId) {
        mModel.loadChannelLottery(pos ,channelId);
    }

    public void loadData(int loadType) {
        mModel.loadDataInfo(loadType);
    }

    @Override
    public void onDataInfoSuccess(int loadType, List<ContentEntity> contents) {
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (isViewNotNull()){
                    mView.finishLoadMore(0, true, contents.size() == AppConst.COUNT_SMAll);
                    mView.showLoadMoreHeadLineData(contents);
                }
                mContents.addAll(contents);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN && isViewNotNull()) {
                    mView.finishRefresh(true);
                }
                mContents.clear();
                mContents.addAll(contents);
                if (isViewNotNull())
                    mView.showHeadLineDataView(mContents);
                break;
        }
    }

    @Override
    public void onDataInfoError(int loadType, String msg) {
        if (isViewNotNull()) {
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    break;
                default:
                    // TODO: 2018/10/3 网络错误
                    break;
            }
        }
    }

    @Override
    public void loadChannelSuccess(List<LotteryChannelEntity> entities) {
        if (isViewNotNull()){
            mView.showLotteryColumn(entities);
            mModel.loadChannelLottery(0  ,entities.get(0).getChannelId());
        }

    }

    @Override
    public void loadChannelLotterySuccess(int pos, LotteryEntity.LotteryResultDtoBean entity) {
        if (isViewNotNull()){
            mView.loadLotteryView(pos ,entity);
        }
    }
}
