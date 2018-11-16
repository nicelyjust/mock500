package com.yunzhou.tdinformation.mine.red.model;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.red
 *  @文件名:   MyRedPacketModel
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 10:35
 *  @描述：
 */

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.List;

public class MyRedPacketModel {

    private final IMyRedPacketModel mModel;

    public MyRedPacketModel(IMyRedPacketModel iMyRedPacketModel) {
        mModel = iMyRedPacketModel;
    }

    public void loadRedPacket(Context context, final int loadType, int uid) {
        OkGo.<JsonResult<DataBean<List<RedPacksBean>>>>get(NetConstant.GET_RED_PACKET +uid + "/allUseful")
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<DataBean<List<RedPacksBean>>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<RedPacksBean>>>> response) {
                        DataBean<List<RedPacksBean>> content = response.body().content;
                        if (content != null && mModel != null) {
                            mModel.loadRedSuccess(loadType ,content.dataMap);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<RedPacksBean>>>> response) {
                        super.onError(response);
                        mModel.loadRedError(loadType ,response.getException().getMessage());
                    }
                });
    }

    public void loadUselessRedPacket(Context context, final int loadType, int uid) {
        OkGo.<JsonResult<DataBean<List<RedPacksBean>>>>get(NetConstant.GET_RED_PACKET +uid + "/allDisable")
                .headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<DataBean<List<RedPacksBean>>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<List<RedPacksBean>>>> response) {
                        DataBean<List<RedPacksBean>> content = response.body().content;
                        if (content != null && mModel != null) {
                            mModel.loadRedSuccess(loadType ,content.dataMap);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<List<RedPacksBean>>>> response) {
                        super.onError(response);
                        mModel.loadRedError(loadType ,response.getException().getMessage());
                    }

                    @Override
                    public JsonResult<DataBean<List<RedPacksBean>>> convertResponse(okhttp3.Response response) throws Throwable {
                        try {
                            JsonResult<DataBean<List<RedPacksBean>>> jsonResult = super.convertResponse(response);
                            List<RedPacksBean> dataMap = jsonResult.content.dataMap;
                            if (dataMap!= null){
                                for (RedPacksBean redPacksBean : dataMap) {
                                    redPacksBean.isExpire = true;
                                }
                            }
                            return jsonResult;
                        } catch (Throwable throwable) {
                            return super.convertResponse(response);
                        }
                    }
                });
    }
}
