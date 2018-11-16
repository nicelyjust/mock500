package com.yunzhou.tdinformation.bean;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean
 *  @文件名:   HelpEntity
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 16:09
 *  @描述：    帮助中心的bean,便于拓展
 */

public class HelpEntity extends Entity {
    public HelpEntity(String subject, String url, String desc) {
        this.subject = subject;
        this.url = url;
        this.desc = desc;
    }

    public String subject;
    public String url;
    public String desc;
}
