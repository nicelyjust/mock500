package com.yunzhou.tdinformation.lottery.history.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.history.model
 *  @文件名:   DiscussModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/30 17:12
 *  @描述：
 */

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.lottery.DiscussEntity;
import com.yunzhou.tdinformation.bean.lottery.GeneralCommentBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscussModel {
    public static final int COUNT = 10;
    private static final String TAG = "DiscussModel";
    private final IDiscussModel mIDiscussModel;
    private int pageNo = 1;
    private JSONObject mJsonObject;
    private JSONObject mContentObject;
    private GeneralCommentBean mCommentBean;
    private int key = -1;

    public DiscussModel(IDiscussModel iDiscussModel) {
        this.mIDiscussModel = iDiscussModel;
    }

    public void loadData(final int loadType, int lotteryId, String expect) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DiscussEntity>>get(NetConstant.LOTTERY_DISCUSS + "/" + lotteryId)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .params(AppConst.Param.PAGE_SIZE, COUNT)
                .params("lotteryExpect", expect)
                .params(AppConst.Param.UID, UserManager.getInstance().getUid())
                .execute(new JsonCallback<JsonResult<DiscussEntity>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DiscussEntity>> response) {
                        DiscussEntity content = response.body().content;
                        if (mIDiscussModel != null) {
                            mIDiscussModel.loadDataSuccess(loadType, content);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DiscussEntity>> response) {
                        super.onError(response);
                        if (mIDiscussModel != null) {
                            mIDiscussModel.loadDataError(loadType, response.getException().getMessage());
                        }
                    }
                });
    }

    public void insertLike(Context context, GeneralCommentBean commentBean) {
        if (mJsonObject == null) {
            mJsonObject = new JSONObject();
        }
        try {
            mJsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
            mJsonObject.put("commentsId", commentBean.getCommentId());
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                mJsonObject.put("commentsId", commentBean.getCommentId());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        final int isPraise = commentBean.getIsPraise();
        OkGo.<JsonResult<Object>>post(NetConstant.LIKE_DISCUSS + isPraise)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        L.d(TAG, response.body().getMessage());
                        mIDiscussModel.insertLikeOk(isPraise);
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        Log.e(TAG, "insertLikeError: " + response.getException().getMessage());
                    }
                });
    }

    public void comment(Context context, final String content, int lotteryId, String expect) {
        if (mContentObject == null)
            mContentObject = new JSONObject();
        try {
            mContentObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
            mContentObject.put(AppConst.Param.CONTENT, content);
            mContentObject.put(AppConst.Param.LOTTERY_ID, lotteryId);
            mContentObject.put(AppConst.Param.LOTTERY_EXPECT, expect);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mContentObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                mContentObject.put(AppConst.Param.CONTENT, content);
                mContentObject.put(AppConst.Param.LOTTERY_ID, lotteryId);
                mContentObject.put(AppConst.Param.LOTTERY_EXPECT, expect);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        String jwt = UserManager.getInstance().getJwt();
        OkGo.<JsonResult<Object>>post(NetConstant.INSERT_DISCUSS_COMMENT)
                .headers(AppConst.Param.JWT, jwt)
                .upJson(mContentObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (mCommentBean == null)
                            mCommentBean = new GeneralCommentBean();
                        mCommentBean.setAvatar(UserManager.getInstance().getUserEntity().getAvatar());
                        mCommentBean.setCommentContent(content);
                        mCommentBean.setNickName(UserManager.getInstance().getUserEntity().getNickName());
                        mCommentBean.setCommentId(key--);
                        if (mIDiscussModel != null) {
                            mIDiscussModel.insertComment(mCommentBean);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: " + response.getException().getMessage());
                        if (mIDiscussModel != null)
                            mIDiscussModel.insertComment(null);
                    }
                });
    }
}
