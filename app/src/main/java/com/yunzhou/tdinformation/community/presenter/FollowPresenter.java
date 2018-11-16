package com.yunzhou.tdinformation.community.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.community.BlogPostedEntity;
import com.yunzhou.tdinformation.community.view.FollowView;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.view
 *  @文件名:   FollowPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 11:22
 *  @描述：
 */

public class FollowPresenter extends WrapperPresenter<FollowView> {
    private static final String TAG = "FollowPresenter";
    private int pageNo = 1;
    private List<Blog> mBlog;
    private JSONObject mJsonObject;

    public FollowPresenter() {
        mBlog = new ArrayList<>();
    }

    public void loadDataInfo(Context context, final int loadType, int uid) {

        mView.showLoading();

        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<BlogPostedEntity>>>get(NetConstant.GET_FOLLOW_POST.concat("/").concat(String.valueOf(uid)))
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_SMAll)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new LoginCallback<JsonResult<DataBean<BlogPostedEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        JsonResult<DataBean<BlogPostedEntity>> body = response.body();
                        List<Blog> list = body.content.dataMap.getList();
                        dealWithSuccess(loadType, list);

                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        super.onError(response);
                        String msg = response.getException().getMessage();
                        dealWithError(loadType, msg);
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

    private void dealWithSuccess(int loadType, List<Blog> blog) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                mView.finishLoadMore(0, true, blog.size() < AppConst.COUNT_SMAll);
                mView.showLoadMoreBlogData(blog);
                mBlog.addAll(blog);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                if (blog == null || blog.isEmpty()) {
                    mView.showEmptyView(View.VISIBLE);
                } else {
                    mView.showEmptyView(View.GONE);
                }
                mBlog.clear();
                if (blog != null)
                    mBlog.addAll(blog);
                mView.showBlogDataView(mBlog);
                break;
        }
    }

    private void dealWithError(int loadType, String msg) {
        mView.hideLoading();
        Log.e(TAG, "loadBlogError: " + msg);
        if (isViewNotNull()) {
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    if (mBlog.isEmpty())
                        mView.showErrorView(View.VISIBLE);
                    break;
                default:
                    mView.showErrorView(View.VISIBLE);
                    break;
            }
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
                .headers(AppConst.Param.JWT ,UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()){
                            mView.showLikeSuccess(preIsLike);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        L.e(TAG, "onError: "+response.getException().getMessage());
                        if (isViewNotNull()){
                            mView.showLikeError(postId);
                        }
                    }
                });

    }
    @Override
    public void destroy() {

    }
}
