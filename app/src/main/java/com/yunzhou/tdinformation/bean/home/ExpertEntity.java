package com.yunzhou.tdinformation.bean.home;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.home
 *  @文件名:   ExpertEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/10 16:58
 *  @描述：
 */

public class ExpertEntity extends Entity {
    /**
     * id : 30
     * nickName : 星睿
     * avatar : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4139463319,3154487591&fm=58&bpow=540&bpoh=720
     */

    private int id;
    private String nickName;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
