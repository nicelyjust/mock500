package com.yunzhou.tdinformation.lottery.lottery.viewholder;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.tdinformation.bean.lottery.LotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.adapter.ProvinceItemAdapter;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.adapter
 *  @文件名:   ProvinceItem
 *  @创建者:   lz
 *  @创建时间:  2018/10/23 12:23
 *  @描述：
 */
public class ProvinceItem extends AbstractExpandableItem<LotteryEntity> implements MultiItemEntity {
    public int id;
    public int pos;
    public String province;

    public ProvinceItem(int id, int pos ,String province) {
        this.id = id;
        this.pos = pos;
        this.province = province;
    }

    @Override
    public int getItemType() {
        return ProvinceItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
