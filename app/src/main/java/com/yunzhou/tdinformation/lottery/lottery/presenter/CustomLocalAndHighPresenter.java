package com.yunzhou.tdinformation.lottery.lottery.presenter;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.lottery.CustomLotteryEntity;
import com.yunzhou.tdinformation.bean.lottery.NotSubscribedBean;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomLocalAndHighView;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.ProvinceLotteryItem;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery.lottery.presenter
 *  @文件名:   CustomLocalAndHighPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/26 13:44
 *  @描述： 本地彩 高频
 */

public class CustomLocalAndHighPresenter extends WrapperPresenter<CustomLocalAndHighView> {
    private static final String TAG = "CustomLAndHPresenter";
    // 0 地方;1 高频彩
    private int mFromWhere;
    private JSONObject mJsonObject;

    public CustomLocalAndHighPresenter(int fromWhere) {
        this.mFromWhere = fromWhere;
    }

    public void loadData() {
        OkGo.<JsonResult<CustomLotteryEntity>>get(NetConstant.FIND_CUSTOM_LOTTERY)
                .params(AppConst.Param.LOTTERY_TYPE, mFromWhere == 0 ? "LOCAL" : "HIGH_RATE")
                .params(AppConst.Param.USER_ID, UserManager.getInstance().getUid())
                .execute(new JsonCallback<JsonResult<CustomLotteryEntity>>() {
                    private ArrayList<MultiItemEntity> realEntity = new ArrayList<>();
                    @Override
                    public void onSuccess(Response<JsonResult<CustomLotteryEntity>> response) {
                        if (!isViewNotNull()) {
                            return;
                        }
                        CustomLotteryEntity content = response.body().content;
                        List<RecommendLotteryEntity> subscribeList = content.getSubscribeList();
                        if (subscribeList != null && !subscribeList.isEmpty())
                            mView.showSubscribeList(subscribeList);
                        if (!realEntity.isEmpty()) {
                            mView.showNotSubscribeList(realEntity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<CustomLotteryEntity>> response) {
                        super.onError(response);
                    }

                    @Override
                    public JsonResult<CustomLotteryEntity> convertResponse(okhttp3.Response response) throws Throwable {
                        JsonResult<CustomLotteryEntity> jsonResult = super.convertResponse(response);
                        List<NotSubscribedBean> notSubscribedDtoList = jsonResult.content.getNotSubscribedDtoList();
                        int size = notSubscribedDtoList.size();
                        realEntity.clear();
                        for (int j = 0; j < size; j++) {
                            NotSubscribedBean notSubscribedBean = notSubscribedDtoList.get(j);
                            ProvinceLotteryItem lv0 = new ProvinceLotteryItem(notSubscribedBean.getProvinceId() ,j, notSubscribedBean.getProvince());
                            List<RecommendLotteryEntity> lotteryList = notSubscribedBean.getLotteryList();
                            int size1 = lotteryList.size();
                            for (int k = 0; k < size1; k++) {
                                lv0.addSubItem(lotteryList.get(k));
                            }
                            realEntity.add(lv0);
                        }
                        return jsonResult;
                    }
                });
    }

    public void unSubscribe(Context context, final RecommendLotteryEntity item) {
        OkGo.<JsonResult<Object>>delete(NetConstant.CUSTOM_LOTTERY + "/" + UserManager.getInstance().getUid()+"/" + item.getId())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.unSubscribeLotterySuccess(item);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.e(TAG, "onError: " + message);
                        if (isViewNotNull()) {
                            mView.unSubscribeLotteryError(message);
                        }
                    }
                });
    }

    public void subscribe(Context context, final RecommendLotteryEntity item) {
        if (mJsonObject == null) {
            mJsonObject = new JSONObject();
        }
        try {
            mJsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
            mJsonObject.put(AppConst.Param.LOTTERY_ID, item.getId());
        } catch (JSONException e) {
            try {
                mJsonObject.put(AppConst.Param.USER_ID, UserManager.getInstance().getUid());
                mJsonObject.put(AppConst.Param.LOTTERY_ID, item.getId());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.CUSTOM_LOTTERY)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.subscribeLotterySuccess(item);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.e(TAG, "onError: " + message);
                        if (isViewNotNull()) {
                            mView.subscribeLotteryError(message);
                        }
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
