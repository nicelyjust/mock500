package com.yunzhou.tdinformation.bean;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   PostCountBean
 *  @创建者:   lz
 *  @创建时间:  2018/11/6 20:07
 *  @描述：
 */

public class PostCountBean extends Entity {
    /**
     * count
     */
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
