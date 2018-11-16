package com.yunzhou.tdinformation.expert;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ExpertPageController
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 19:05
 *  @描述：
 */

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.expert.ExpertIntroEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.expert.view.ExpertPageView;
import com.yunzhou.tdinformation.user.UserManager;

public class ExpertPageController {
    private static final String TAG = "ExpertPageController";
    private ExpertPageView mView;

    public ExpertPageController(ExpertPageView view) {
        mView = view;
    }

    public void loadData(int uid, int expertId) {
        OkGo.<JsonResult<DataBean<ExpertIntroEntity>>>get(NetConstant.GET_USER_INFO + "/" + uid + "/expert/" + expertId)
                .execute(new JsonCallback<JsonResult<DataBean<ExpertIntroEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<ExpertIntroEntity>>> response) {
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            ExpertIntroEntity introEntity = response.body().content.dataMap;
                            if (introEntity != null)
                                mView.showExpert(introEntity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<ExpertIntroEntity>>> response) {
                        super.onError(response);
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            String message = response.getException().getMessage();
                            Log.e(TAG, "onError: " + message);
                        }
                    }
                });
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public boolean isViewNotNull() {
        return mView != null;
    }

    public void requestFollowExpert(Context context, final boolean hasFollow, int expertId) {
        String url;
        if (hasFollow) {
            url = NetConstant.UN_FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + expertId;
        } else {
            url = NetConstant.FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + expertId;
        }
        String jwt = UserManager.getInstance().getJwt();
        if (hasFollow) {
            OkGo.<JsonResult<Object>>delete(url)
                    .headers(AppConst.Param.JWT, jwt)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull())
                                mView.showFollowSuccess(hasFollow ,response.body().metaBean.msg);
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            if (isViewNotNull())
                                mView.showFollowError(hasFollow ,response.getException().getMessage());
                        }
                    });
            return;
        }

        OkGo.<JsonResult<Object>>post(url)
                .headers(AppConst.Param.JWT, jwt)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull())
                            mView.showFollowSuccess(hasFollow ,response.body().metaBean.msg);
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull())
                            mView.showFollowError(hasFollow ,response.getException().getMessage());
                    }
                });
    }

}
