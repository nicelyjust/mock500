package com.yunzhou.tdinformation.constant;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.constant
 *  @文件名:   NetConstant
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 19:30
 *  @描述：
 */

public class NetConstant {

    private static final String BASE_RELEASE_URL = "http://192.168.2.20";
    private static final String BASE_RC_URL = "http://192.168.2.20";
    public static final String BASE_URL = BASE_RELEASE_URL;
    private static final String BASE_INFO_URL = BASE_URL + ":8081";//资讯类

    public static final String BASE_USER_INFO_URL = BASE_URL + ":8085";//用户信息相关

    public static final String BASE_REWORD_INFO_URL = BASE_URL + ":8082";//开奖信息

    public static final String BASE_PAY_URL = BASE_URL +":8086";//支付地址

    public static final String BASE_BANNER_URL = BASE_URL +":8087";//轮播图和公告
    public static final String BASE_UPLOAD_URL = BASE_URL +":9000";//上传图片地址

    public static final String BASE_ARTICLE_URL = BASE_URL + ":8080/examples/zixun/zixun.html";//文章的路径
    /*------------ 用户信息相关 -------------*/
    public static final String SEND_AUTH_URL = BASE_USER_INFO_URL + "/sms/send";
    public static final String VERIFY_AUTH_URL = BASE_USER_INFO_URL + "/sms/verify";
    public static final String LOGIN_URL = BASE_USER_INFO_URL + "/webuser/login";
    public static final String LOGIN_OUT = BASE_USER_INFO_URL + "/webuser/logout";
    public static final String REGISTER_URL = BASE_USER_INFO_URL + "/webuser/register";
    public static final String BINDING_URL = BASE_USER_INFO_URL + "/webuser/";
    //文章列表接口
    public static final String CONTENT_LIST_URL = BASE_INFO_URL + "/app/content/list";
    //首页获取频道
    public static final String GET_CHANNEL_COLUMN = BASE_INFO_URL + "/app/channel/getChannelSelect";
    // 首页推荐专家列表
    public static final String GET_RECOMMEND_EXPERT = BASE_USER_INFO_URL + "/app/user/list";

    public static final String GET_USER_INFO = BASE_USER_INFO_URL + "/app/user";
    // http://39.108.61.68:8085/app/attention/add/48/37
    public static final String FOLLOW_USER = BASE_USER_INFO_URL + "/app/attention/add";
    public static final String UN_FOLLOW_USER = BASE_USER_INFO_URL + "/app/attention/cancel";
    //轮播图和公告
    public static final String GET_BANNER_AND_REPORT = BASE_BANNER_URL + "/carousel/allUseful";
    public static final String GET_CAMPAIGN = BASE_BANNER_URL + "/carousel/findActivity";
    /*------------ 社区 -------------*/
    public static final String GET_ADMIN_POST = BASE_INFO_URL + "/app/post/getAdminPost";
    public static final String GET_GROUND_POST = BASE_INFO_URL + "/app/post/queryGroundPost";
    public static final String GET_CIRCLE_TYPE = BASE_INFO_URL + "/app/post/queryCircle";
    public static final String ADD_POST = BASE_INFO_URL + "/app/post/add";
    public static final String QUERY_CIRCLE_TYPE = BASE_INFO_URL + "/app/post/queryByCircleType";
    public static final String GET_FOLLOW_POST = BASE_INFO_URL + "/app/post/queryByMyAttention";
    public static final String ADD_POST_COMMENT = BASE_INFO_URL + "/app/post/insertPostComments";
    // /app/collect/add/{uid}/{id}/{type} 文章type是1；帖子是2
    public static final String ADD_COLLECT = BASE_USER_INFO_URL + "/app/collect/add/";
    public static final String CANCEL_COLLECT = BASE_USER_INFO_URL + "/app/collect/cancel/";//delete

    // /{uid}/{post_id}?pageIndex=1&pageSize=10
    public static final String GET_POST_DETAIL = BASE_INFO_URL + "/app/post/detail";
    // 喜欢或者取消喜欢 post
    public static final String LIKE_POST = BASE_INFO_URL + "/app/like/insertLike";
    // json{uid  commentsId}
    public static final String LIKE_DISCUSS = BASE_INFO_URL + "/app/lotteryPraise?isPraise=";

    public static final String INSERT_DISCUSS_COMMENT = BASE_INFO_URL + "/app/comments/insertLotteryComment";

    //8081/app/content/findContentByExpect/1?expect=2018126
    public static final String NEXT_LOTTERY_PREDICTION = BASE_INFO_URL + "/app/content/findContentByExpect";
    // /app/comments/queryLotteryCommentsByLotteryId/1?pageIndex=1&pageSize=10&lotteryExpect=2018126&uid=45
    public static final String LOTTERY_DISCUSS = BASE_INFO_URL + "/app/comments/queryLotteryCommentsByLotteryId";
    // http://39.108.61.68:8085/app/user/expert/37/article?pageIndex=1&pageSize=2&orderByView=false
    public static final String GET_EXPERT_ARTICLE = BASE_USER_INFO_URL + "/app/user/expert";
    /*------------ 开奖模块 -------------*/
    public static final String GET_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/result/findProvinceList";
    public static final String GET_COUNTRY_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/result/findNationWideList";
    // 历史开奖
    public static final String GET_HISTORY_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/result/findResultListByDate";
    // 查询对应彩种最新开奖结果
    public static final String GET_SPECIAL_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/result";
    // 定制相关主地址
    public static final String CUSTOM_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/customized";

    public static final String INSERT_BATCH_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/customized/insertBatch";

    public static final String FIND_CUSTOM_LOTTERY = BASE_REWORD_INFO_URL + "/app/lottery/result/findSubscribeList";

    public static final String RECOMMEND_CUSTOM_LOTTERY = BASE_REWORD_INFO_URL + "/lottery/notSubscribedList";
    ///app/lottery/detail/findLotteryDetail?lotteryId=1&expect=2018123
    public static final String GET_LOTTERY_DETAIL = BASE_REWORD_INFO_URL + "/app/lottery/detail/findLotteryDetail";
    /*------------ 支付模块 -------------*/
    // 需要jwt
    public static final String ADD_ODER_POST = BASE_PAY_URL + "/order/addOrder";
    // 获取支付jwt
    public static final String ADD_PAY_JWT = BASE_PAY_URL + "/trade/issuePayJWT";

    public static final String PAY = BASE_PAY_URL + "/order/payOrder";

    public static final String UP_LOAD_IMG = BASE_UPLOAD_URL + "/fastdfs/upload";

    /*------------ 会员操作 -------------*/
    // /app/user/ 带ID
    public static final String BASE_USER_OPERATOR_URL = BASE_USER_INFO_URL + "/app/user/";
    public static final String UPDATE_LOGIN_PASSWORD = BASE_USER_INFO_URL + "/webuser/updatePsd";
    // /app/fan/findMyFansList/22?pageIndex=1&pageSize=10粉丝列表
    public static final String GET_FANS = BASE_USER_INFO_URL + "/app/fan/findMyFansList/";
    // /app/attention/findMyFollowList/22?pageIndex=1&pageSize=10
    public static final String GET_MY_FOLLOW = BASE_USER_INFO_URL + "/app/attention/findMyFollowList/";

    public static final String GET_OTHER_FOLLOW = BASE_USER_INFO_URL + "/app/attention/findOtherFollowList/";
    ///app/fan/findMyFansList/
    public static final String GET_MY_FANS = BASE_USER_INFO_URL + "/app/fan/findMyFansList/";

    public static final String GET_OTHER_FANS = BASE_USER_INFO_URL + "/app/fan/findOtherFansList/";
    // 浏览历史 /app/user/22/history?pageIndex=1&pageSize=10
    public static final String GET_MY_POST_COUNT = BASE_USER_INFO_URL + "/app/post/countMyPosts/";

    // 已付费文章 /app/user/37/bought?pageIndex=1&pageSize=1
    // /app/collect/findMyCollectList/%zd/%d",(Uid),(Type)]
    public static final String GET_MY_COLLECT = BASE_USER_INFO_URL + "/app/collect/findMyCollectList/";
    // /redpack/:userId/allUseful
    public static final String GET_RED_PACKET = BASE_BANNER_URL + "/redpack/";

    public static final String GET_MY_POST = BASE_USER_INFO_URL + "/app/post/queryByMyPost/";
    // postId
    public static final String DELETE_POST = BASE_USER_INFO_URL + "/app/post/deleteMyPost/";

}
