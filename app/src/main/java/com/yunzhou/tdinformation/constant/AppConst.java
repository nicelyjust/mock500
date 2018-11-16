package com.yunzhou.tdinformation.constant;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.constant
 *  @文件名:   AppConst
 *  @创建者:   lz
 *  @创建时间:  2018/9/25 19:14
 *  @描述：
 */

public class AppConst {
    public static final int LOAD_TYPE_NORMAL = 0x1;
    // 上拉加载更多
    public static final int LOAD_TYPE_UP = 0x2;
    // 下拉刷新
    public static final int LOAD_TYPE_DOWN = 0x3;

    public static final int COUNT = 7;
    public static final int COUNT_MAX = 10;
    public static final int COUNT_SMAll = 5;
    public static final String SHARE_FILE_NAME = "share_file_name";


    public static class Param {
        public static final String APP_ID = "appId";
        public static final String PAGE_SIZE = "pageSize";
        public static final String PAGE_NO = "pageNo";
        public static final String PAGE_INDEX = "pageIndex";
        public static final String PHONE_NUM = "phone";
        public static final String PASSWORD = "pasWord";
        public static final String V_AUTH_CODE = "vCode";
        public static final String AUTH_CODE = "code";
        public static final String CLIENT_TYPE= "clientType";
        public static final String LOGIN_TYPE= "webLoginType";
        public static final String JWT = "jwt";
        public static final String PAY_JWT = "payjwt";
        public static final String PARENT_ID = "parentId";
        public static final String CHANNEL_ID = "channelId";

        public static final String BIZ_ID = "bizId";
        public static final String ORDER_MONEY = "orderMoney";
        public static final String ORDER_NUM = "orderNum";
        public static final String ORDER_PASSWORD = "payPassword";
        public static final String RESOURCE_TYPE = "resourceType";
        public static final int RESOURCE_TYPE_ARTICLE = 1;
        public static final String UID = "uid";
        public static final String USER_ID = "userId";
        public static final String LOTTERY_TYPE = "lotteryType";
        public static final String LOTTERY_ID = "lotteryId";
        public static final String EXPECT = "expect";
        public static final String LOTTERY_EXPECT = "lotteryExpect";
        public static final String CONTENT = "content";


        public static final String NICK_NAME = "nickName";
        public static final String AVATAR = "avatar";
        public static final String REAL_NAME = "realName";
        public static final String CITY = "city";
        public static final String ID_CARD = "idCard";
        public static final String OLD_PAY_PASSWORD = "oldPayPassword";
        public static final String GENDER = "gender";
        public static final String BANK_CARD = "bankCard";
        public static final String EMAIL = "email";
        public static final String BIRTHDAY = "birthday";
        public static final String INTRO = "intro";
        public static final String MESSAGE_TOGGLES = "messageToggles";
        /**
         * 当传入此参数为3时，不验证旧支付密码，直接修改 { "id": 用户ID "payPassword":"MTIzNDU2", "alreadySetPayPass": 3 }
         */
        public static final String ALREADY_SET_PAY_PASS = "alreadySetPayPass";
        public static final String ID = "id";
        public static final String REGISTER = "register";
        public static final String OLD_PHONE_NUM = "oldPhone";
        public static final String NEW_PHONE_NUM = "newPhone";
        public static final String PAY_PASSWORD = "payPassword";
    }

    public static class Extra {
        public static final String CONTENT_ID = "contentId";
        public static final String USER_ID = "uid";
        public static final String USER_SP_NAME = "yzUser";

        public static final String USER = "user";
        public static final String USER_JWT = "user_jwt";
        public static final String POST_ID = "post_id";
        public static final String CIRCLE_TYPE = "circle_type";
        public static final String CIRCLE_TYPE_NAME = "circle_type_name";
        public static final String EXPERT_ID = "expert_id";
        public static final String EXPERT_HEAD_URL = "expert_head_url";
        public static final String ODER_NO = "oderNo";
        public static final String CAN_USE_PACKET = "canUsePacket";
        public static final String PAY_TYPE_BEAN = "payTypeBean";
        public static final String RED_PACK = "redPack";
        public static final String SHARE_VALUES_IMAGES = "share_values_images";
        public static final String SHARE_VALUES_CONTENT = "share_values_content";
        public static final String CHART_URL = "chartUrl";
        public static final String FROM_WHERE = "from_where";
        public static final String LOTTERY_RESULT = "lottery_result";
        public static final String LOTTERY_NAME = "lottery_name";
        public static final String LOTTERY_EXPECT = "lottery_expect";
        public static final String SHOW_TYPE = "show_type";
    }

    public static class JS {
        public static final String PAY_MONEY = "payMoney";
        public static final String ESSAY_ID = "essayId";
        public static final String MONEY = "money";
        public static final String SEND_KEY = "sendKey";
        public static final String LOGIN = "toLogin";
    }

    public static class Request {
        public static final int LOGIN = 1000;
        public static final int MODIFY_NICKNAME = 1001;
        public static final int MODIFY_SIGN = 1002;
        public static final int MODIFY_GENDER = 1003;
        public static final int MODIFY_EMAIL = 1004;
        public static final int MODIFY_PAY_PASSWORD = 1005;
    }
    public static class Action {
        public static final String REFRESH_ACCOUNT = "com.yunzhou.action.refresh.account";
    }
}
