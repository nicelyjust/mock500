package com.yunzhou.tdinformation.community.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community.model
 *  @文件名:   GroundModel
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 17:39
 *  @描述：
 */

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.community.BlogPostedEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;

import java.util.List;

public class GroundModel {
    private IGroundPresenter mPresenter;
    private int pageNo = 1;

    public GroundModel(IGroundPresenter presenter) {
        this.mPresenter = presenter;
    }

    public void loadDataInfo(final int loadType, int uid) {
        if (loadType == AppConst.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        OkGo.<JsonResult<DataBean<BlogPostedEntity>>>get(NetConstant.GET_GROUND_POST.concat("/").concat(String.valueOf(uid)))
                .params(AppConst.Param.PAGE_SIZE, AppConst.COUNT_SMAll)
                .params(AppConst.Param.PAGE_INDEX, pageNo)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new JsonCallback<JsonResult<DataBean<BlogPostedEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        JsonResult<DataBean<BlogPostedEntity>> body = response.body();
                        List<Blog> list = body.content.dataMap.getList();
                        mPresenter.loadBlogSuccess(loadType, list);
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<BlogPostedEntity>>> response) {
                        super.onError(response);
                        String msg = response.getException().getMessage();
                        mPresenter.loadBlogError(loadType, msg);
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

    public void loadAdminPost(int uid) {

        OkGo.<JsonResult<DataBean<Blog>>>get(NetConstant.GET_ADMIN_POST.concat("/").concat(String.valueOf(uid)))
                .execute(new JsonCallback<JsonResult<DataBean<Blog>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<Blog>>> response) {
                        JsonResult<DataBean<Blog>> body = response.body();
                        Blog blog = body.content.dataMap;
                        if (blog == null) {
                            mPresenter.loadAdminPostError("data is null");
                        } else {
                            mPresenter.loadAdminPostSuccess(blog);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<Blog>>> response) {
                        super.onError(response);
                        String msg = response.getException().getMessage();
                        mPresenter.loadAdminPostError(msg);
                    }

                    @Override
                    public JsonResult<DataBean<Blog>> convertResponse(okhttp3.Response response) throws Throwable {
                        JsonResult<DataBean<Blog>> result = super.convertResponse(response);
                        result.content.dataMap.init();
                        return result;
                    }
                });



    }
}
