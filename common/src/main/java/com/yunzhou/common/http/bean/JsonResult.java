package com.yunzhou.common.http.bean;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.JsonResponse;
import com.yunzhou.common.http.bean.base.MetaBean;

/**
 * Created by LuoTao
 */
public class JsonResult<T> extends JsonResponse {

    //全局相应
    @SerializedName("meta")
    public MetaBean metaBean;

    /**
     * 判断此次结果是否成功
     * @return 是否成功
     */
    public boolean isSuccess(){
        return (null != metaBean) && metaBean.ok;
    }

    /**
     * 获取服务器返回的消息
     * @return 返回的消息
     */
    public String getMessage(){
        if(null == metaBean)
            return "";
        return metaBean.msg;
    }

    // 请求的返回内容
    @SerializedName("data")
    public T content;
}
