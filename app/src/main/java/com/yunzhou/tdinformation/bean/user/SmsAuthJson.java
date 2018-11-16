package com.yunzhou.tdinformation.bean.user;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http.bean.user
 *  @文件名:   SmsAuthJson
 *  @创建者:   lz
 *  @创建时间:  2018/9/29 11:02
 *  @描述：
 */

public class SmsAuthJson {

    /**
     * ret : {"code":200,"msg":"1102","obj":3628}
     */

    private RetEntity ret;

    public RetEntity getRet() {
        return ret;
    }

    public void setRet(RetEntity ret) {
        this.ret = ret;
    }

    public static class RetEntity {
        /**
         * code : 200
         * msg : 1102
         * obj : 3628
         */

        private int code;
        private String msg;
        private int obj;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getObj() {
            return obj;
        }

        public void setObj(int obj) {
            this.obj = obj;
        }
    }

}
