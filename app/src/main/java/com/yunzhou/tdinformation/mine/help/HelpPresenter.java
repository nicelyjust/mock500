package com.yunzhou.tdinformation.mine.help;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.HelpEntity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.help
 *  @文件名:   HelpPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 15:39
 *  @描述：
 */

public class HelpPresenter extends WrapperPresenter<HelpView> {

    private final List<HelpEntity> mData;

    public HelpPresenter() {
        mData = new ArrayList<>(10);
    }

    public void loadData(int loadType) {
        mockData();
    }

    private void mockData() {
        mData.clear();
        HelpEntity entity1 = new HelpEntity("关于今日云彩" ,"http://39.108.61.68:8080/examples/help/help.html",null);
        HelpEntity entity2 = new HelpEntity("如何充值?" ,"http://39.108.61.68:8080/examples/help/help.html",null);
        HelpEntity entity3 = new HelpEntity("为什么要实名制?" ,"http://39.108.61.68:8080/examples/help/help.html",null);
        HelpEntity entity4 = new HelpEntity("账户信息或银行卡号填写有误？" ,"http://39.108.61.68:8080/examples/help/help.html",null);
        HelpEntity entity5 = new HelpEntity("忘记用户名或密码怎么办？" ,"http://39.108.61.68:8080/examples/help/help.html",null);
        HelpEntity entity6 = new HelpEntity("拨打客服电话" ,"","400-820-8820");
        mData.add(entity1);
        mData.add(entity2);
        mData.add(entity3);
        mData.add(entity4);
        mData.add(entity5);
        mData.add(entity6);
        if (isViewNotNull()) {
            mView.showDataView(mData);
        }
    }

    @Override
    public void destroy() {

    }
}
