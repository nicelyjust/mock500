package com.yunzhou.tdinformation.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.JsonUtil;
import com.yunzhou.common.utils.SpUtil;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.UiUtils;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.bean.JsonBean;
import com.yunzhou.tdinformation.bean.blog.UploadImgEntity;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.utils.AppOperator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   UserPresenter
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 14:12
 *  @描述：
 */

public class UserPresenter extends WrapperPresenter<UserView> {
    // 省数据
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private JSONObject mJsonObject;
    private JSONObject mJsonObject1;

    public ArrayList<JsonBean> getOptions1Items() {
        return options1Items;
    }

    public ArrayList<ArrayList<String>> getOptions2Items() {
        return options2Items;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getOptions3Items() {
        return options3Items;
    }

    // 城市数据
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    // 地区数据
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public boolean isLoading() {
        return loading;
    }

    private volatile boolean loading = false;

    public void parseProvince(final boolean isPredictLoad) {
        AppOperator.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                loading = true;
                String result = JsonUtil.getAssertJsonStr(UiUtils.getApp(), "province.json");
                ArrayList<JsonBean> detail = new ArrayList<>();
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i < data.length(); i++) {
                        JsonBean entity = JsonUtil.getGson().fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                        detail.add(entity);
                    }
                    options1Items.clear();
                    options1Items.addAll(detail);
                    int size = detail.size();
                    for (int i = 0; i < size; i++) {//遍历省份
                        ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                        ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                        for (int c = 0; c < detail.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                            String cityName = detail.get(i).getCityList().get(c).getName();
                            cityList.add(cityName);//添加城市
                            ArrayList<String> cityAreaList = new ArrayList<>();//该城市的所有地区列表

                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (detail.get(i).getCityList().get(c).getArea() == null
                                    || detail.get(i).getCityList().get(c).getArea().size() == 0) {
                                cityAreaList.add("");
                            } else {
                                cityAreaList.addAll(detail.get(i).getCityList().get(c).getArea());
                            }
                            Province_AreaList.add(cityAreaList);//添加该省所有地区数据
                        }
                        options2Items.add(cityList);
                        options3Items.add(Province_AreaList);
                    }
                    loading = false;
                    if (!isPredictLoad && isViewNotNull()) {

                        mView.showCity();
                    }
                } catch (Exception e) {
                    options1Items.clear();
                    options2Items.clear();
                    options3Items.clear();
                    loading = false;
                    if (!isPredictLoad && isViewNotNull()) {
                        mView.showCityError();
                    }
                    e.printStackTrace();

                }
            }
        });
    }

    public boolean hasProvinceData() {
        return !options1Items.isEmpty();
    }

    public void onActivityResult(int requestCode, Intent data) {
        // 修改成功的返回
        String extra = data.getStringExtra("extra");
        if (isViewNotNull())
            switch (requestCode) {
                case AppConst.Request.MODIFY_NICKNAME:
                    mView.showModifyNickName(extra);
                    break;
                case AppConst.Request.MODIFY_SIGN:
                    mView.showModifySign(extra);
                    break;
                case AppConst.Request.MODIFY_GENDER:
                default:
                    mView.showModifyGender(extra);
                    break;

            }
    }
    public void modifyCity(Context context, final String newCity) {
        if (!TDevice.hasInternet()) {
            return;
        }
        if (newCity.equals(UserManager.getInstance().getUserEntity().getCity())){
            return;
        }
        if (mJsonObject == null)
            mJsonObject = new JSONObject();
        try {
            mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
            mJsonObject.put(AppConst.Param.CITY, newCity);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
                mJsonObject.put(AppConst.Param.CITY, newCity);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>put(NetConstant.BASE_USER_OPERATOR_URL + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.showModifyCity(newCity);
                            UserEntity userEntity = UserManager.getInstance().getUserEntity();
                            userEntity.setCity(newCity);
                            saveUserInfo(userEntity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull()) {
                            mView.showModifyCityError();
                        }
                    }
                });
    }

    private void saveUserInfo(UserEntity userEntity) {
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).put(AppConst.Extra.USER, JsonUtil.jsonString(userEntity));
    }
    public void upHead(File file) {
        mView.showLoading("保存头像中...");
        OkGo.<JsonResult<DataBean<UploadImgEntity>>>post(NetConstant.UP_LOAD_IMG)
                .params("file", file)
                .execute(new JsonCallback<JsonResult<DataBean<UploadImgEntity>>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<UploadImgEntity>>> response) {
                        UploadImgEntity imgEntity = response.body().content.dataMap;
                        if (isViewNotNull()){
                            mView.deleteCacheImg();
                            mView.showModify(imgEntity);
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<UploadImgEntity>>> response) {
                        super.onError(response);
                        if (isViewNotNull()) {
                            mView.deleteCacheImg();
                            mView.hideLoading();
                        }
                    }
                });
    }
    public void saveHead(final Context context, String originUrl, final String thumbUrl){
        if (mJsonObject1 == null)
            mJsonObject1 = new JSONObject();
        String avatarUrl = originUrl.concat("@@").concat(thumbUrl);
        try {
            mJsonObject1.put(AppConst.Param.ID, UserManager.getInstance().getUid());
            mJsonObject1.put(AppConst.Param.AVATAR, avatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject1.put(AppConst.Param.ID, UserManager.getInstance().getUid());
                mJsonObject1.put(AppConst.Param.AVATAR, avatarUrl);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>put(NetConstant.BASE_USER_OPERATOR_URL + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject1)
                .execute(new LoginCallback<JsonResult<Object>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            UserEntity userEntity = UserManager.getInstance().getUserEntity();
                            userEntity.setAvatar(thumbUrl);
                            saveUserInfo(userEntity);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(AppConst.Action.REFRESH_ACCOUNT));
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (isViewNotNull()) {
                            mView.hideLoading();
                            mView.showModifyAvatarError();
                        }
                    }
                });
    }
    @Override
    public void destroy() {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        options1Items = null;
        options2Items = null;
        options3Items = null;
    }
}
