package com.yunzhou.common.http.bean;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.JsonResponse;

/**
 * Created by Luotao on 2018/9/15 21:50
 * Phone : +86 13560749417
 * E-Mail Address：gtkrockets@163.com
 */
public class DataBean<T> extends JsonResponse {
    @SerializedName(value = "dataMap",alternate = {"data","dataList" ,"ret","webUserModel","paths","content"})
    public T dataMap ;

    @SerializedName(value = "jwt" ,alternate = {"lotteryName"})
    public String jwt;
}
