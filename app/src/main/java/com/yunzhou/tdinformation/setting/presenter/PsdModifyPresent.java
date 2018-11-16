package com.yunzhou.tdinformation.setting.presenter;

import android.text.TextUtils;
import android.util.Base64;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.base.mvp.WrapperPresenter;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.setting.view.PsdModifyView;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/11/8.
 */

public class PsdModifyPresent extends WrapperPresenter<PsdModifyView> {

    private static final String TAG = "PsdModifyPresent";

    public void onPsdModify(String oldPayPassword, String newPayPassword) {

        JSONObject obj = new JSONObject();
        try {
            obj.put(AppConst.Param.ID, UserManager.getInstance().getUid());
            obj.put(AppConst.Param.PAY_PASSWORD, Base64.encodeToString(newPayPassword.getBytes(), Base64.NO_WRAP));
            if (TextUtils.isEmpty(oldPayPassword)) {
                obj.put(AppConst.Param.ALREADY_SET_PAY_PASS, 3);
            } else {
                obj.put(AppConst.Param.OLD_PAY_PASSWORD, Base64.encodeToString(oldPayPassword.getBytes(), Base64.NO_WRAP));
            }

        } catch (JSONException e) {
            try {
                obj.put(AppConst.Param.ID, UserManager.getInstance().getUid());
                obj.put(AppConst.Param.PAY_PASSWORD, newPayPassword);
                if (TextUtils.isEmpty(oldPayPassword)) {
                    obj.put(AppConst.Param.ALREADY_SET_PAY_PASS, 3);
                } else {
                    obj.put(AppConst.Param.OLD_PAY_PASSWORD, oldPayPassword);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>put(NetConstant.BASE_USER_OPERATOR_URL + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(obj)
                .execute(new JsonCallback<JsonResult<Object>>() {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (isViewNotNull())
                            mView.onModifySuccess();
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        String message = response.getException().getMessage();
                        L.d(TAG, "onError: " + message);
                        mView.onModifyError(message);
                    }
                });
    }
    @Override
    public void destroy() {

    }
}
