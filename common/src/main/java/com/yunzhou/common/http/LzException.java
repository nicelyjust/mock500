package com.yunzhou.common.http;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http
 *  @文件名:   LzException
 *  @创建者:   lz
 *  @创建时间:  2018/9/29 20:07
 *  @描述：    根据网络请求自定义异常,方便error里处理
 */

public class LzException extends RuntimeException{
    public static final int NEED_LOGIN = 1008;
    static final long serialVersionUID = -184124673093119416L;
    private int mCode;
    private String mMsg;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public LzException(int code , String msg) {
        this.mCode = code;
        this.mMsg = msg;
    }

    public LzException(String message) {
        super(message);
        this.mMsg = message;
    }

    @Override
    public String getMessage() {
        return this.mMsg;
    }

}
