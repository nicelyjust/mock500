package com.yunzhou.tdinformation.community.presenter;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.community.model.GroundModel;
import com.yunzhou.tdinformation.community.model.IGroundPresenter;
import com.yunzhou.tdinformation.community.view.GroundView;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.presenter
 *  @文件名:   GroundPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 17:14
 *  @描述：
 */

public class GroundPresenter extends WrapperPresenter<GroundView> implements IGroundPresenter {

    private static final String TAG = "GroundPresenter";
    private final GroundModel mModel;
    public List<Blog> mBlog;
    private JSONObject mJsonObject;

    public GroundPresenter() {
        mModel = new GroundModel(this);
        mBlog = new ArrayList<>();
    }


    public void loadDataInfo(int loadType, int uid) {
        mModel.loadDataInfo(loadType, uid);
    }

    @Override
    public void destroy() {

    }

    public void loadAdminPost(int uid) {
        mModel.loadAdminPost(uid);
    }

    @Override
    public void loadBlogSuccess(int loadType, List<Blog> blog) {
        if (!isViewNotNull()){
            return;
        }
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                mView.finishLoadMore(0, true, blog.size() == AppConst.COUNT_SMAll);
                mView.showLoadMoreBlogData(blog);
                mBlog.addAll(blog);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                mBlog.clear();
                mBlog.addAll(blog);
                mView.showBlogDataView(mBlog);
                break;
        }
    }

    @Override
    public void loadBlogError(int loadType, String msg) {
        Log.e(TAG, "loadBlogError: " + msg);
        if (isViewNotNull()) {
            switch (loadType) {
                case AppConst.LOAD_TYPE_UP:

                    mView.finishLoadMore(0, false, true);
                    break;
                case AppConst.LOAD_TYPE_DOWN:
                    mView.finishRefresh(false);
                    break;
                default:
                    // TODO: 2018/10/3 网络错误
                    break;
            }
        }
    }

    @Override
    public void loadAdminPostSuccess(Blog blog) {
        // 插入到第一行,考虑拓展,不只显示一个公告
        if (isViewNotNull()) {
            mView.showAdminPost(blog);
        }
    }

    @Override
    public void loadAdminPostError(String msg) {
        Log.e(TAG, "loadAdminPostError: " + msg);
        // 移除管理员公告
        if (isViewNotNull()) {
            mView.showAdminPostErrorView();
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
}
