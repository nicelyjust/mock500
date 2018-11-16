package com.yunzhou.tdinformation.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   GenderModifyFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 18:01
 *  @描述：
 */

public class EmailModifyFragment extends AbsModifyFragment {
    @BindView(R.id.et_nickname)
    EditText mEditText;
    @BindView(R.id.tv_left_word_count)
    TextView mTvLeftWordCount;
    private String mContent;
    private JSONObject mJsonObject;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single_modify;
    }
    @Override
    protected void initWidget(View root) {
        mTvLeftWordCount.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mExtra)) {
            mEditText.setText(mExtra);
            mEditText.setSelection(mExtra.length());
        }
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEditText.setMaxLines(1);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mContent = s.toString().trim();
                mCallback.setFinishEnabled(!TextUtils.isEmpty(mContent) && mContent.equals(mExtra));
            }
        });
    }

    public static EmailModifyFragment newInstance(String email) {
        Bundle args = new Bundle();
        args.putString("extra", email);
        EmailModifyFragment fragment = new EmailModifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean save() {
        // 验证邮箱格式
        if (!TDevice.hasInternet()) {
            return false;
        }
        if (mJsonObject == null)
            mJsonObject = new JSONObject();
        try {
            mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
            mJsonObject.put(AppConst.Param.EMAIL, mContent);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mJsonObject.put(AppConst.Param.ID, UserManager.getInstance().getUid());
                mJsonObject.put(AppConst.Param.EMAIL, mContent);
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
                            userEntity.setEmail(mContent);
                            saveUserInfo(userEntity);
                            hideLoading();
                            ToastUtil.showShort(mContext, "修改邮箱成功");

                            Intent intent = getActivity().getIntent();
                            intent.putExtra("extra" , mContent);
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<Object>> response) {
                        super.onError(response);
                        if (mContext != null) {
                            hideLoading();
                            ToastUtil.showShort(mContext, "修改邮箱失败");
                        }
                    }
                });
        return true;
    }
}
