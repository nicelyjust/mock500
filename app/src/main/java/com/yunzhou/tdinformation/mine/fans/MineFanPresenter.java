package com.yunzhou.tdinformation.mine.fans;

import android.content.Context;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.FanEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.follow
 *  @文件名:   MineFollowPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/7 9:28
 *  @描述：
 */

public class MineFanPresenter extends WrapperPresenter<MineFanView> {

    private static final String TAG = "MineFanPresenter";
    private List<FanEntity.FanBean> mData;
    private int pageNo = 1;

    public MineFanPresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(Context context, final int loadType, int uid) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        GetRequest<JsonResult<DataBean<FanEntity>>> request;
        if (UserManager.getInstance().isMine(uid)) {
            request = OkGo.get(NetConstant.GET_MY_FANS + UserManager.getInstance().getUid());
        } else {
            request = OkGo.get(NetConstant.GET_OTHER_FANS + UserManager.getInstance().getUid() + "/" + uid);
        }
        request.headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_MAX)
                .execute(new LoginCallback<JsonResult<DataBean<FanEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<FanEntity>>> response) {
                        FanEntity dataMap = response.body().content.dataMap;
                        if (dataMap != null) {
                            loadFollowsSuccess(loadType, dataMap.getList());
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<FanEntity>>> response) {
                        super.onError(response);
                        loadFollowsError(loadType, response.getException().getMessage());
                    }
                });
    }

    private void loadFollowsSuccess(int loadType, List<FanEntity.FanBean> list) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (list != null) {
                    mView.finishLoadMore(0, true, list.size() < AppConst.COUNT_MAX);
                    mView.showLoadMoreData(list);
                    mData.addAll(list);
                }
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (list == null || list.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                    return;
                }
                mData.clear();
                mData.addAll(list);
                mView.showErrorView(View.GONE);
                mView.showDataView(mData);
                break;
        }
    }

    private void loadFollowsError(int loadType, String msg) {
        L.e(TAG, "loadBlogError: " + msg);
        if (isViewNotNull()) {
            mView.hideLoading();
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    if (mData.isEmpty())
                        mView.showErrorView(View.VISIBLE);
                    break;
                default:
                    mView.showErrorView(View.VISIBLE);
                    break;
            }
        }
    }

    public void requestFollow(Context context, boolean hasFollow, int uId, final int pos) {
        String url;
        if (hasFollow) {
            url = NetConstant.UN_FOLLOW_USER  + "/" + UserManager.getInstance().getUid()+ "/" + uId;
        } else {
            url = NetConstant.FOLLOW_USER  + "/" + UserManager.getInstance().getUid() + "/" + uId;
        }
        String jwt = UserManager.getInstance().getJwt();
        if (hasFollow) {
            OkGo.<JsonResult<Object>>delete(url)
                    .headers(AppConst.Param.JWT, jwt)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull())
                                mView.showFollowSuccess(response.body().metaBean.msg, pos);
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            if (isViewNotNull())
                                mView.showFollowError(response.getException().getMessage());
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
                            mView.showFollowSuccess(response.body().metaBean.msg, pos);
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull())
                            mView.showFollowError(response.getException().getMessage());
                    }
                });
    }

    @Override
    public void destroy() {
        mData.clear();
        mData = null;
    }
}
