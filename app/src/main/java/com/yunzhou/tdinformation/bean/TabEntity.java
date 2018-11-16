package com.yunzhou.tdinformation.bean;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   TabEntity
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 18:37
 *  @描述：
 */

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
