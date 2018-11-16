package com.yunzhou.tdinformation.bean.user;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http.bean.user
 *  @文件名:   UserJson
 *  @创建者:   lz
 *  @创建时间:  2018/9/29 10:29
 *  @描述：
 */

public class UserJson {

    /**
     * user : {"id":1,"password":null,"jwt":null,"username":"admin","nickName":"社区管理员","avatar":"NULL","realName":"社区管理员","gender":2,"email":"wd@s.com","phone":"13512345678","birthday":"2018-09-12T16:00:00.000+0000","status":null,"gid":1,"intro":"0","bravo":0,"goodAt":"0","attentionCount":0,"fansCount":0,"grade":"普通会员","city":null,"idCard":null,"payPassword":null,"oldPayPassword":null,"alreadySetPayPass":0,"watchExp":0,"contentsNum":0,"isAdmin":1,"bankCard":null}
     */

    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
