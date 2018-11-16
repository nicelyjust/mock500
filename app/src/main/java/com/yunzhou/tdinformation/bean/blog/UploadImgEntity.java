package com.yunzhou.tdinformation.bean.blog;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.blog
 *  @文件名:   UploadImgEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/22 15:07
 *  @描述：
 */

public class UploadImgEntity extends Entity {
    /**
     * originPath : http://39.108.61.68:80/group1/M00/00/0A/rBKZOFvNdseAJM7EAAAbkW-TkKk694.png
     * thumbPath : http://39.108.61.68:80/group1/M00/00/0A/rBKZOFvNdseAeID6AAAge8WUBNc456.png
     */
    private String originPath;
    private String thumbPath;

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
}
