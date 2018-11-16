package com.yunzhou.tdinformation.mine.feedback;

import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.HelpEntity;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.feedback
 *  @文件名:   FeedbackPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 18:59
 *  @描述：
 */

public class FeedbackPresenter extends WrapperPresenter<FeedbackView> {

    private List<HelpEntity> mData;

    public FeedbackPresenter() {
        mData = new ArrayList<>(10);
    }

    @Override
    public void destroy() {
        mData.clear();
        mData = null;
    }

    public void loadData(int loadTypeUp) {
        mData.clear();
        HelpEntity entity1 = new HelpEntity("意见反馈：用的不爽，我有话说" ,"",null);
        HelpEntity entity2 = new HelpEntity("功能询问：哪些功能不了解，不会用" ,"",null);
        HelpEntity entity3 = new HelpEntity("产品需求：想要加一个这样的功能" ,"",null);
        HelpEntity entity4 = new HelpEntity("产品bug：功能故障或不能正常使用" ,"",null);
        HelpEntity entity5 = new HelpEntity("性能问题：卡顿/闪退、发热/耗电" ,"",null);
        HelpEntity entity6 = new HelpEntity("彩币关于：充值遇到问题，余额异常" ,"","");
        HelpEntity entity7 = new HelpEntity("无法获取验证码" ,"","");
        HelpEntity entity8 = new HelpEntity("无法登录" ,"","");
        HelpEntity entity9 = new HelpEntity("其他问题" ,"","");
        mData.add(entity1);
        mData.add(entity2);
        mData.add(entity3);
        mData.add(entity4);
        mData.add(entity5);
        mData.add(entity6);
        mData.add(entity7);
        mData.add(entity8);
        mData.add(entity9);
        if (isViewNotNull()) {
            mView.showDataView(mData);
        }
    }
}
