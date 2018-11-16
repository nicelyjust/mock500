package com.yunzhou.common.http.bean.base;

import com.google.gson.annotations.SerializedName;
public class MetaBean extends JsonResponse {
    /**
     * 后台传入的消息
     */
    @SerializedName("msg")
    public String msg = "";

    /**
     * 响应码
     */
    @SerializedName("code")
    public int code;

    /**
     * 是否成功
     */
    @SerializedName("success")
    public boolean ok;

    /**
     * 时间戳
     */
    @SerializedName("timestamp")
    public String timestamp;
}
