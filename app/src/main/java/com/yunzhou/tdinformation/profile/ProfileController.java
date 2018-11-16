package com.yunzhou.tdinformation.profile;

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
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.bean.CheckFollowEntity;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.community.BlogPostedEntity;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    private static final String TAG = "ProfileController";
    private ProfileView mView;
    private int pageNo = 1;
    private JSONObject mJsonObject;
    private List<Blog> mData;

    public ProfileController(ProfileView view) {
        mView = view;
        mData = new ArrayList<>();
    }

    public void loadUserInfo(Context context, int uid) {
        OkGo.<JsonResult<DataBean<UserEntity>>>get(NetConstant.GET_USER_INFO + "/" + uid)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<DataBean<UserEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<UserEntity>>> response) {
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            UserEntity entity = response.body().content.dataMap;
                            if (entity != null)
                                mView.showUserInfo(entity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<UserEntity>>> response) {
                        super.onError(response);
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            String message = response.getException().getMessage();
                            Log.e(TAG, "onError: " + message);
                        }
                    }
                });
    }

    public void loadBlogListData(Context context, final int loadType, int uid) {
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showLoading();
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        String jwt = UserManager.getInstance().getJwt();
        OkGo.<JsonResult<DataBean<BlogPostedEntity>>>get(NetConstant.GET_MY_POST + uid)
                .headers(AppConst.Param.JWT, jwt)
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_SMAll)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new LoginCallback<JsonResult<DataBean<BlogPostedEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        JsonResult<DataBean<BlogPostedEntity>> body = response.body();
                        List<Blog> list = body.content.dataMap.getList();
                        loadBlogSuccess(loadType, list);
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        super.onError(response);
                        String msg = response.getException().getMessage();
                        loadBlogError(loadType, msg);
                    }

                    @Override
                    public JsonResult<DataBean<BlogPostedEntity>> convertResponse(okhttp3.Response response) throws Throwable {
                        JsonResult<DataBean<BlogPostedEntity>> jsonResult = super.convertResponse(response);
                        List<Blog> blogList = jsonResult.content.dataMap.getList();
                        for (Blog blog : blogList) {
                            blog.init();
                        }
                        return jsonResult;
                    }
                });
    }

    private void loadBlogSuccess(int loadType, List<Blog> list) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                if (list != null) {
                    mView.finishLoadMore(0, true, list.size() < AppConst.COUNT_SMAll);
                    mView.showLoadMoreBlogData(list);
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
                mView.showBlogDataView(mData);
                break;
        }
    }

    private void loadBlogError(int loadType, String msg) {
        Log.e(TAG, "loadBlogError: " + msg);
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

    public void requestFollowUser(Context context, final boolean hasFollow, int uid) {
        String url;
        if (hasFollow) {
            url = NetConstant.UN_FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + uid;
        } else {
            url = NetConstant.FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + uid;
        }
        String jwt = UserManager.getInstance().getJwt();
        if (hasFollow) {
            OkGo.<JsonResult<Object>>delete(url)
                    .headers(AppConst.Param.JWT, jwt)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull())
                                mView.showFollowSuccess(hasFollow, response.body().metaBean.msg);
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            if (isViewNotNull())
                                mView.showFollowError(hasFollow, response.getException().getMessage());
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
                            mView.showFollowSuccess(hasFollow, response.body().metaBean.msg);
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull())
                            mView.showFollowError(hasFollow, response.getException().getMessage());
                    }
                });
    }

    public boolean isViewNotNull() {
        return mView != null;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public void addCollect(Context context, final int postId, final int preIsCollect) {
        if (preIsCollect == 0) {
            OkGo.<JsonResult<Object>>get(NetConstant.ADD_COLLECT + UserManager.getInstance().getUid() + "/" + postId + "/2")
                    .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull()) {
                                mView.showCollectSuccess(preIsCollect);
                            }
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            L.e(TAG, "onError: " + response.getException().getMessage());
                            if (isViewNotNull()) {
                                mView.showCollectError(preIsCollect);
                            }
                        }
                    });
        } else {
            OkGo.<JsonResult<Object>>delete(NetConstant.CANCEL_COLLECT + UserManager.getInstance().getUid() + "/" + postId + "/2")
                    .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull()) {
                                mView.showCollectSuccess(preIsCollect);
                            }
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            L.e(TAG, "onError: " + response.getException().getMessage());
                            if (isViewNotNull()) {
                                mView.showCollectError(preIsCollect);
                            }
                        }
                    });
        }
    }

    public void insertLike(Context context, final int postId, final int preIsLike) {
        if (mJsonObject == null) {
            mJsonObject = new JSONObject();
        }
        try {
            mJsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
            mJsonObject.put("postId", postId);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                mJsonObject.put("postId", postId);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.LIKE_POST)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.showLikeSuccess(preIsLike);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        L.e(TAG, "onError: " + response.getException().getMessage());
                        if (isViewNotNull()) {
                            mView.showLikeError(postId);
                        }
                    }
                });

    }

    // 8085/app/user/:uid/expert/:expUid/checkWatch
    public void checkFollow(Context context, int uid) {
        OkGo.<JsonResult<CheckFollowEntity>>get(NetConstant.BASE_USER_OPERATOR_URL + UserManager.getInstance().getUid() + "/expert/" + uid + "/checkWatch")
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<CheckFollowEntity>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<CheckFollowEntity>> response) {
                        CheckFollowEntity content = response.body().content;
                        if (isViewNotNull()) {
                            mView.showFollowView(content.getData());
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<CheckFollowEntity>> response) {
                        super.onError(response);
                        L.e(TAG, response.getException().getMessage());
                    }
                });
    }
}
