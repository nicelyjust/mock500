package com.yunzhou.tdinformation.user;

import com.yunzhou.tdinformation.base.mvp.BaseView;
import com.yunzhou.tdinformation.bean.blog.UploadImgEntity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   UserView
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 14:13
 *  @描述：
 */

public interface UserView extends BaseView {
    void showLoading(String tip);

    // 子线程中注意
    void showCity();

    void showCityError();

    void showModifyNickName(String extra);

    void showModifySign(String extra);

    void showModifyGender(String extra);

    void showModifyCity(String newCity);

    void showModifyCityError();

    void showModify(UploadImgEntity imgEntity);

    void deleteCacheImg();

    void showModifyAvatarError();

}
