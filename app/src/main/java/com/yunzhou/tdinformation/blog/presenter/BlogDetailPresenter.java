package com.yunzhou.tdinformation.blog.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   BlogDetailPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 16:28
 *  @描述：
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.blog.CommentChildBean;
import com.yunzhou.tdinformation.bean.blog.PostCommentEntity;
import com.yunzhou.tdinformation.bean.blog.PostEntity;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.blog.view.BlogDetailView;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogDetailPresenter extends WrapperPresenter<BlogDetailView> {

    private static final String TAG = "BlogDetailPresenter";
    private int pageNo = 1;

    private List<PostCommentEntity> mData;
    private JSONObject mJsonObject;
    private JSONObject mJsonObject1;
    private Blog mBlog;

    public BlogDetailPresenter() {
        mData = new ArrayList<>();
    }

    public void loadData(final int loadType, int postId) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }

        OkGo.<JsonResult<PostEntity>>get(NetConstant.GET_POST_DETAIL + "/" + UserManager.getInstance().getUid() + "/" + postId)
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_SMAll)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .execute(new JsonCallback<JsonResult<PostEntity>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<PostEntity>> response) {
                        PostEntity postEntity = response.body().content;
                        dealWithSuccess(loadType, postEntity);
                    }

                    @Override
                    public void onError(Response<JsonResult<PostEntity>> response) {
                        super.onError(response);
                        dealWithError(loadType, response.getException().getMessage());
                    }

                    @Override
                    public JsonResult<PostEntity> convertResponse(okhttp3.Response response) throws Throwable {
                        JsonResult<PostEntity> jsonResult = super.convertResponse(response);
                        try {
                            List<PostCommentEntity> postCommentList = jsonResult.content.getCommits().getPostCommentList();
                            for (PostCommentEntity postCommentEntity : postCommentList) {
                                L.d(postCommentEntity.getCommentBeans().size());
                                for (PostCommentEntity commentEntity : postCommentList) {
                                    Collections.sort(commentEntity.getCommentBeans());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "convertResponse: " + e.getMessage());
                        }
                        return jsonResult;
                    }
                });
    }

    private void dealWithSuccess(int loadType, PostEntity postEntity) {
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        mBlog = postEntity.getBlog();
        if (mBlog == null) {
            mView.showError(View.VISIBLE);
            return;
        }
        List<PostCommentEntity> postCommentList = postEntity.getCommits().getPostCommentList();
        switch (loadType) {
            case AppConst.LOAD_TYPE_UP:
                mView.finishLoadMore(0, true, postEntity.getCommits().isIsLastPage());
                mView.showLoadMoreCommentData(postCommentList);
                mData.addAll(postCommentList);
                break;
            default:
                if (loadType == AppConst.LOAD_TYPE_NORMAL && (postCommentList == null || postCommentList.isEmpty())) {
                    mView.showNoCommentsView(mBlog);
                }
                if (loadType == AppConst.LOAD_TYPE_DOWN) {
                    mView.finishRefresh(true);
                }
                mData.clear();
                if (postCommentList != null) {
                    mData.addAll(postCommentList);
                    mView.showBlogDataView(mData, mBlog);
                }
                break;
        }
    }

    private void dealWithError(int loadType, String message) {
        L.e(TAG, message);
        if (!isViewNotNull()) {
            return;
        }
        mView.hideLoading();
        if (loadType == AppConst.LOAD_TYPE_NORMAL)
            mView.showError(View.VISIBLE);
    }

    public void addCollect(Context context, int postId, final int preIsCollect) {
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

    public void insertLike(Context context, int postId, final int preIsLike) {
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
                            mView.showLikeError();
                        }
                    }
                });

    }

    public void requestFollow(Context context, boolean hasFollow, int uId) {
        String url;
        if (hasFollow) {
            url = NetConstant.UN_FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + uId;
        } else {
            url = NetConstant.FOLLOW_USER + "/" + UserManager.getInstance().getUid() + "/" + uId;
        }
        String jwt = UserManager.getInstance().getJwt();
        if (hasFollow) {
            OkGo.<JsonResult<Object>>delete(url)
                    .headers(AppConst.Param.JWT, jwt)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull())
                                mView.showFollowSuccess();
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
                            mView.showFollowSuccess();
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull())
                            mView.showFollowError(response.getException().getMessage());
                    }
                });
    }

    public void insertLikeComment(Context context, final int position, final int preIsLike, int commentsId) {
        if (mJsonObject1 == null) {
            mJsonObject1 = new JSONObject();
        }
        try {
            mJsonObject1.put(AppConst.Param.UID, UserManager.getInstance().getUid());
            mJsonObject1.put("commentsId", commentsId);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject1.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                mJsonObject1.put("commentsId", commentsId);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>post(NetConstant.LIKE_POST)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject1)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull())
                            mView.insertLikeCommentOk(position, preIsLike);
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.e(TAG, "insertLikeError: " + message);
                        if (isViewNotNull())
                            mView.insertLikeCommentError(message);
                    }
                });
    }

    public void sendComment(Context context, final int position, final PostCommentEntity commentItem, final CommentChildBean commentChildBean, final String content) {

        JSONObject jsonObject = new JSONObject();
        PostRequest<JsonResult<Object>> postRequest = OkGo.<JsonResult<Object>>post(NetConstant.ADD_POST_COMMENT)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt());
        if (position == -1) {
            // 代表回复post
            if (mBlog != null) {
                try {
                    jsonObject.put("postId", mBlog.getPostId());
                    jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                    jsonObject.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        jsonObject.put("postId", mBlog.getPostId());
                        jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                        jsonObject.put("content", content);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                postRequest.upJson(jsonObject)
                        .execute(new LoginCallback<JsonResult<Object>>(context) {
                            @Override
                            public void onSuccess(Response<JsonResult<Object>> response) {
                                if (isViewNotNull()) {
                                    mView.showAddMainComment(content);
                                }
                            }

                            @Override
                            public void onError(Response<JsonResult<Object>> response) {
                                super.onError(response);
                                if (isViewNotNull()) {
                                    mView.showCommentFailed(response.getException().getMessage());
                                }
                            }
                        });
            }
        } else if (commentItem == null && commentChildBean != null) {
            try {
                jsonObject.put("postId", mBlog.getPostId());
                jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                jsonObject.put("content", content);
                jsonObject.put("parentId", commentChildBean.getChildUserId());
                jsonObject.put("rootId", commentChildBean.rootUid);
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    jsonObject.put("postId", mBlog.getPostId());
                    jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                    jsonObject.put("content", content);
                    jsonObject.put("parentId", commentChildBean.getChildUserId());
                    jsonObject.put("rootId", commentChildBean.rootUid);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            postRequest.upJson(jsonObject)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull()) {
                                mView.showAddComment(position ,commentChildBean);
                            }
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            if (isViewNotNull()) {
                                mView.showCommentFailed(response.getException().getMessage());
                            }
                        }
                    });
        } else if (commentItem != null && commentChildBean == null) {
            try {
                jsonObject.put("postId", mBlog.getPostId());
                jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                jsonObject.put("content", content);
                jsonObject.put("rootId", commentItem.getCommentsId());
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    jsonObject.put("postId", mBlog.getPostId());
                    jsonObject.put(AppConst.Param.UID, UserManager.getInstance().getUid());
                    jsonObject.put("content", content);
                    jsonObject.put("rootId", commentItem.getCommentsId());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            postRequest.upJson(jsonObject)
                    .execute(new LoginCallback<JsonResult<Object>>(context) {
                        @Override
                        public void onSuccess(Response<JsonResult<Object>> response) {
                            if (isViewNotNull()) {
                                mView.showAddChildComment(position);
                            }
                        }

                        @Override
                        public void onError(Response<JsonResult<Object>> response) {
                            super.onError(response);
                            if (isViewNotNull()) {
                                mView.showCommentFailed(response.getException().getMessage());
                            }
                        }
                    });
        }

    }

    @NonNull
    public PostCommentEntity buildPostCommentEntity(String content) {
        PostCommentEntity item = new PostCommentEntity();
        item.setAvatar(UserManager.getInstance().getUserEntity().getAvatar());
        item.setNickName(UserManager.getInstance().getUserEntity().getNickName());
        item.setCreateTime(String.valueOf(System.currentTimeMillis()));
        item.setContent(content);
        return item;
    }
    /**
     * 构建回复某条子评论
     * @return
     */
    @NonNull
    public CommentChildBean buildCommentChildBean(CommentChildBean commentChildBean) {
        CommentChildBean childBean = new CommentChildBean();
        childBean.setChildUserId(UserManager.getInstance().getUid());
        childBean.setChildUserName(UserManager.getInstance().getUserEntity().getNickName());
        childBean.setParentUserId(commentChildBean.getChildUserId());
        childBean.setParentUserName(commentChildBean.getChildUserName());
        return childBean;
    }

    /**
     * 构建回复某条评论
     * @return
     */
    @NonNull
    public CommentChildBean buildCommentChildBean() {
        CommentChildBean childBean = new CommentChildBean();
        childBean.setChildUserId(UserManager.getInstance().getUid());
        childBean.setChildUserName(UserManager.getInstance().getUserEntity().getNickName());
        return childBean;
    }

    @Override
    public void destroy() {

    }
}
