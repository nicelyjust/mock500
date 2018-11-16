package com.yunzhou.tdinformation.user;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   UserManager
 *  @创建者:   lz
 *  @创建时间:  2018/9/29 15:24
 *  @描述：    当前用户信息管理类
 */

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yunzhou.common.utils.JsonUtil;
import com.yunzhou.common.utils.SpUtil;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.utils.ActivityStackManager;

public class UserManager {
    private volatile static UserManager instance;
    public static final int UN_LOGIN = -1;
    private UserEntity userEntity;
    private  String jwt;

    public String getJwt() {
        if (TextUtils.isEmpty(jwt)) {
            this.jwt = SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).getString(AppConst.Extra.USER_JWT);
        }
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    private UserManager() {
    }

    public synchronized static UserManager getInstance() {
        if (instance == null) {
            synchronized (ActivityStackManager.class){
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }
    public void  setUserInfo(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    public @Nullable UserEntity getUserEntity() {

        if (userEntity == null) {
            //内存不足被回收,此时应该去file或者DB亦或SP去读
            SpUtil instance = SpUtil.getInstance(AppConst.Extra.USER_SP_NAME);
            String json = instance.getString(AppConst.Extra.USER);
            return TextUtils.isEmpty(json) ? null : JsonUtil.jsonToBean(json ,UserEntity.class);
        }else{
            return this.userEntity;
        }
    }
    public boolean isMine(int uid){
        if (getUserEntity() == null) {
            return false;
        }
        return getUserEntity().getId() == uid;
    }
    /**
     *
     * @return 缺省 -1 未登录
     */
    public synchronized int getUid(){
        UserEntity userEntity = getUserEntity();
        return userEntity == null ? UN_LOGIN : userEntity.getId();
    }

    public boolean isLogin() {
        return getUid() != -1;
    }

    public synchronized void clear(){
        this.userEntity = null;
        this.jwt = null;
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).remove(AppConst.Extra.USER);
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).remove(AppConst.Extra.USER_JWT);
    }
    public synchronized void save(UserEntity userEntity){
        this.userEntity = userEntity;
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).put(AppConst.Extra.USER, JsonUtil.jsonString(userEntity));
    }
}
