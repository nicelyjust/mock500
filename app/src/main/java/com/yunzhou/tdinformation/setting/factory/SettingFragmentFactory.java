package com.yunzhou.tdinformation.setting.factory;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.setting.factory
 *  @文件名:   SettingFragmentFactory
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:40
 *  @描述：
 */

import com.yunzhou.tdinformation.setting.AbsSettingFragment;
import com.yunzhou.tdinformation.setting.SettingAccountFragment;
import com.yunzhou.tdinformation.setting.SettingMainFragment;
import com.yunzhou.tdinformation.setting.SettingMessageFragment;

public class SettingFragmentFactory {
    public final static int MainTag = 0;
    //账户安全
    public final static int AccountTag = 1;
    //消息设置
    public final static int MessageTag = 2;

    public AbsSettingFragment createFragment(int tag){
        AbsSettingFragment abstractMainFragment = null;
        switch (tag){
            case MainTag:
                abstractMainFragment = new SettingMainFragment();
                break;
            case AccountTag:
                abstractMainFragment = new SettingAccountFragment();
                break;
            case MessageTag:
                abstractMainFragment = new SettingMessageFragment();
                break;
        }
        return abstractMainFragment;
    }
}
