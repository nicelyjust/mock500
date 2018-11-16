package com.yunzhou.tdinformation.blog.presenter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   PostBlogPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/10/19 13:38
 *  @描述：
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.JsonUtil;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.SpUtil;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.blog.BlogPostBean;
import com.yunzhou.tdinformation.bean.blog.TagBean;
import com.yunzhou.tdinformation.bean.blog.UploadImgEntity;
import com.yunzhou.tdinformation.blog.view.PostBlogView;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.media.Util;
import com.yunzhou.tdinformation.user.UserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PostBlogPresenter extends WrapperPresenter<PostBlogView> {
    private static final String TAG = "PostBlogPresenter";
    private List<BlogPostBean.Pictures> mImgUrlBeans = new ArrayList<>();
    private int count;

    public @Nullable
    String loadContentFromCache() {
        return SpUtil.getInstance(AppConst.SHARE_FILE_NAME).getString(AppConst.Extra.SHARE_VALUES_CONTENT, null);
    }

    public @Nullable
    String[] loadImgFromCache() {
        Set<String> set = SpUtil.getInstance(AppConst.SHARE_FILE_NAME).getStringSet(AppConst.Extra.SHARE_VALUES_IMAGES, null);
        if (set != null && set.size() > 0) {
            return Util.toArray(set);
        }
        return null;
    }

    public List<TagBean> getList(String[] array) {
        List<TagBean> tagBeans = new ArrayList<>(array.length);
        for (String s : array) {
            TagBean bean = new TagBean();
            bean.tagName = s;
            tagBeans.add(bean);
        }
        return tagBeans;
    }

    public void saveContentFromCache(String trim) {
        if (!TextUtils.isEmpty(trim))
            SpUtil.getInstance(AppConst.SHARE_FILE_NAME).put(AppConst.Extra.SHARE_VALUES_CONTENT, trim);
    }

    public void saveImgFromCache(String[] paths) {
        if (paths != null && paths.length > 0)
            SpUtil.getInstance(AppConst.SHARE_FILE_NAME).put(AppConst.Extra.SHARE_VALUES_IMAGES, Util.toHashSet(paths));
    }

    public void postBlog(Context context, String content, String[] imgPaths ,String tag) {
        // 先判断
        if (TextUtils.isEmpty(content) && (imgPaths == null || imgPaths.length < 0)) {
            mView.showMessage("请输入内容");
            return;
        }
        if (TextUtils.isEmpty(tag)){
            mView.showMessage("请选择标签");
            return;
        }
        final BlogPostBean postBean = new BlogPostBean();
        postBean.setCircleType(getCircleType(tag));
        postBean.setContent(TextUtils.isEmpty(content) ? "" : content);
        postBean.setUid(UserManager.getInstance().getUid());
        // 先上传图片,再发帖
        if (imgPaths == null) {
            realPost(context, postBean);
        } else {
            final int length = imgPaths.length;
            for (int i = 0; i < length; i++) {
                mView.showWaitDialog("正在上传图片");

                final BlogPostBean.Pictures imgUrlBean;
                if (mImgUrlBeans.size() == length) {
                    // 把成功的计算在内,数量后边才能对
                    count++;
                    // 说明进来上传部分失败的
                    imgUrlBean = mImgUrlBeans.get(i);
                } else {
                    imgUrlBean = new BlogPostBean.Pictures();
                    imgUrlBean.srcImg = imgPaths[i];
                }
                File file = new File(imgUrlBean.srcImg);
                OkGo.<JsonResult<DataBean<UploadImgEntity>>>post(NetConstant.UP_LOAD_IMG)
                        .params("file", file)
                        .execute(new JsonCallback<JsonResult<DataBean<UploadImgEntity>>>() {
                            @Override
                            public void onSuccess(Response<JsonResult<DataBean<UploadImgEntity>>> response) {
                                UploadImgEntity imgEntity = response.body().content.dataMap;
                                L.d(imgEntity.getOriginPath() + "small:" + imgEntity.getThumbPath());
                                imgUrlBean.setSmallImg(imgEntity.getThumbPath());
                                imgUrlBean.setOriginalImg(imgEntity.getOriginPath());
                                imgUrlBean.isUpload = true;
                                if (!mImgUrlBeans.contains(imgUrlBean))
                                    mImgUrlBeans.add(imgUrlBean);
                                //判断是否有失败的,提示用户重新上传,否则进入发帖
                                if (isViewNotNull())
                                    judgeImgIfFailed(++count, length, postBean);
                            }

                            @Override
                            public void onError(Response<JsonResult<DataBean<UploadImgEntity>>> response) {
                                super.onError(response);
                                if (isViewNotNull())
                                    mView.hideLoading();
                                L.e(TAG, "onError: " + response.getException().getMessage());
                                imgUrlBean.isUpload = false;
                                if (!mImgUrlBeans.contains(imgUrlBean))
                                    mImgUrlBeans.add(imgUrlBean);
                                if (isViewNotNull())
                                    judgeImgIfFailed(++count, length, postBean);
                            }
                        });
            }
        }


    }

    private void judgeImgIfFailed(int i, int length, BlogPostBean postBean) {
        if (i == length) {
            //reset count
            count = 0;
            int j = 0;
            for (BlogPostBean.Pictures urlBean : mImgUrlBeans) {
                if (!urlBean.isUpload) {
                    j++;
                }
            }
            postBean.setImgList(mImgUrlBeans);
            if (j == 0) {
                mView.showUploadImgSuccess();
                realPost(mView.getContext(), postBean);
            } else
                mView.showUploadImgError(length, j);
        }
    }

    public void realPost(final Context context, final BlogPostBean postBean) {
        L.d(postBean.toString());
        mView.showWaitDialog("正在发表...");
        OkGo.<JsonResult<Object>>post(NetConstant.ADD_POST)
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(JsonUtil.jsonString(postBean))
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        removeXmlCache();
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            mView.showRealPostSuccess();
                        }

                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: " + response.getException().getMessage());
                        if (isViewNotNull()) {
                            // 发帖失败,是否重新发送
                            mView.hideLoading();
                            mView.showRealPostError(postBean);
                        }

                    }
                });
    }

    private void removeXmlCache() {
        SpUtil.getInstance(AppConst.SHARE_FILE_NAME).remove(AppConst.Extra.SHARE_VALUES_CONTENT);
        SpUtil.getInstance(AppConst.SHARE_FILE_NAME).remove(AppConst.Extra.SHARE_VALUES_IMAGES);
    }

    public void reset() {
        mImgUrlBeans.clear();
    }

    private int getCircleType(String tag) {
        int type;
        switch (tag) {
            case "数字达人":
                type = 1;
                break;
            case "足球俱乐部":
                type = 2;
                break;
            case "篮球天地":
                type = 3;
                break;
            case "高频彩":
                type = 4;
                break;
            case "吐槽反馈":
                type = 5;
                break;
            default:
                type = 5;
                break;
        }
        return type;
    }

    @Override
    public void destroy() {
        reset();
    }
}
