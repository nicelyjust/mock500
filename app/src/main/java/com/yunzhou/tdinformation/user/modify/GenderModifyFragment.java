package com.yunzhou.tdinformation.user.modify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.user.modify.base.AbsModifyFragment;
import com.yunzhou.tdinformation.user.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   GenderModifyFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 18:01
 *  @描述：
 */

public class GenderModifyFragment extends AbsModifyFragment {

    @BindView(R.id.iv_gentle)
    ImageView mIvGentle;
    @BindView(R.id.rl_gentle)
    RelativeLayout mRlGentle;
    @BindView(R.id.iv_lady)
    ImageView mIvLady;
    @BindView(R.id.rl_lady)
    RelativeLayout mRlLady;
    // 0未设置
    private int curGender;
    private JSONObject mJsonObject;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gender_modify;
    }

    public static GenderModifyFragment newInstance(String gender) {

        Bundle args = new Bundle();
        args.putString("extra", gender);
        GenderModifyFragment fragment = new GenderModifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        if (!TextUtils.isEmpty(mExtra)) {
            if (mExtra.equals("1")){
                curGender = 1;
                mIvGentle.setVisibility(View.VISIBLE);
                mIvLady.setVisibility(View.GONE);
            } else if (mExtra.equals("2")){
                curGender = 2;
                mIvGentle.setVisibility(View.GONE);
                mIvLady.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean save() {
        if (!TDevice.hasInternet()) {
            return false;
        }
        if (mJsonObject == null)
            mJsonObject = new JSONObject();
        try {
            mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
            mJsonObject.put(AppConst.Param.GENDER, curGender);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
                mJsonObject.put(AppConst.Param.GENDER, curGender);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        OkGo.<JsonResult<Object>>put(NetConstant.BASE_USER_OPERATOR_URL + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .upJson(mJsonObject)
                .execute(new LoginCallback<JsonResult<Object>>(mContext) {
                    @Override
                    public void onSuccess(Response<JsonResult<Object>> response) {
                        if (getActivity() != null) {
                            UserEntity userEntity = UserManager.getInstance().getUserEntity();
                            userEntity.setGender(curGender);
                            saveUserInfo(userEntity);
                            hideLoading();
                            ToastUtil.showShort(mContext, "修改性别成功");

                            Intent intent = getActivity().getIntent();
                            intent.putExtra("extra" , String.valueOf(curGender));
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (mContext != null) {
                            hideLoading();
                            ToastUtil.showShort(mContext, "修改性别失败");
                        }
                    }
                });
        return true;
    }

    @OnClick({R.id.rl_gentle, R.id.rl_lady})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_gentle:
                mIvGentle.setVisibility(View.VISIBLE);
                mIvLady.setVisibility(View.GONE);
                curGender = 1;
                mCallback.setFinishEnabled(mExtra.equals("0") || mExtra.equals("2"));
                break;
            case R.id.rl_lady:
                mIvGentle.setVisibility(View.GONE);
                mIvLady.setVisibility(View.VISIBLE);
                curGender = 2;
                mCallback.setFinishEnabled(mExtra.equals("0") || mExtra.equals("1"));
                break;
        }
    }
}
