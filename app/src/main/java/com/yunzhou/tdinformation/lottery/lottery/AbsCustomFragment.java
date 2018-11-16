package com.yunzhou.tdinformation.lottery.lottery;

import android.support.annotation.Nullable;

import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment2;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery
 *  @文件名:   AbsCustomFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/27 10:34
 *  @描述：
 */

public abstract class AbsCustomFragment extends LazyBaseFragment2 {
    public List<RecommendLotteryEntity> mRecordSubscribeList = new ArrayList<>(12);
    // 是否增删定制
    public boolean hasCustom;
    /**
     * 点击了save custom
     */
    public @Nullable List<RecommendLotteryEntity> saveOrCancelSubscribe(){
        return hasCustom ? mRecordSubscribeList : null;
    }

    /**
     *  增加定制
     * @param entity 实体
     */
    public void addSubscribe(RecommendLotteryEntity entity) {
        if (mRecordSubscribeList.size() > 10) {
            ToastUtil.showShort(mContext ,"最多定制10种");
            return;
        }
        if (!mRecordSubscribeList.contains(entity)) {
            hasCustom = true;
            mRecordSubscribeList.add(entity);
        }

    }

    /**
     *  移除定制
     * @param entity 实体
     */
    public void removeSubscribe(RecommendLotteryEntity entity) {
        if (mRecordSubscribeList != null) {
            hasCustom = true;
            mRecordSubscribeList.remove(entity);
        }
    }

    /**
     *  First 增加定制
     * @param entities 实体集合
     */
    public void addAllSubscribe(List<RecommendLotteryEntity> entities) {
        mRecordSubscribeList.clear();
        mRecordSubscribeList.addAll(entities);
    }
}
