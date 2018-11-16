package com.yunzhou.tdinformation.setting;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   SettingMainFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:46
 *  @描述：
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.mine.widget.SettingItem;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.WaitDialog;

import butterknife.BindView;

public class SettingMainFragment extends AbsSettingFragment implements View.OnClickListener {

    @BindView(R.id.si_setting_account)
    SettingItem mSiSettingAccount;
    @BindView(R.id.si_setting_notice)
    SettingItem mSiSettingNotice;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    private ISettingCallback mISettingCallback;
    private WaitDialog mWaitDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mISettingCallback = (ISettingCallback) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement ISettingCallback");
        }
    }

    @Override
    protected void initWidget(View root) {
        mBtnLogout.setOnClickListener(this);
        mSiSettingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISettingCallback != null) {
                    mISettingCallback.onSettingAccountClick();
                }
            }
        });
        mSiSettingNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISettingCallback != null) {
                    mISettingCallback.onSettingNoticeClick();
                }
            }
        });
    }

    @Override
    public String getTitle() {
        return "设置";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                showLoading();
                OkGo.<JsonResult<Object>>post(NetConstant.LOGIN_OUT).headers(AppConst.Param.JWT , UserManager.getInstance().getJwt())
                        .execute(new LoginCallback<JsonResult<Object>>(mContext) {
                            @Override
                            public void onSuccess(Response<JsonResult<Object>> response) {
                                UserManager.getInstance().clear();
                                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(AppConst.Action.REFRESH_ACCOUNT));
                                if (mContext != null)
                                    hideLoading();
                            }
                            @Override
                            public void onError(Response<JsonResult<Object>> response) {
                                super.onError(response);
                                if (mContext != null) {
                                    hideLoading();
                                    L.d(response.getException().getMessage());
                                    ToastUtil.showShort(mContext ,"退出失败,请重试");
                                }

                            }
                        });
                break;
            default:
                break;
        }
    }

    private void hideLoading() {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
    }

    private void showLoading() {
        if (mWaitDialog == null) {
            mWaitDialog = new WaitDialog(mContext);
            mWaitDialog.setCanceledOnTouchOutside(false);
            mWaitDialog.setRemindTxt("");
        }
        mWaitDialog.show();
    }
}
