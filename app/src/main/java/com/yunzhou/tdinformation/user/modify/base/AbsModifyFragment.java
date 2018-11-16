package com.yunzhou.tdinformation.user.modify.base;

import android.content.Context;
import android.os.Bundle;

import com.yunzhou.common.utils.JsonUtil;
import com.yunzhou.common.utils.SpUtil;
import com.yunzhou.tdinformation.base.fragment.BaseFragment;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.view.WaitDialog;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   AbsModifyFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 18:51
 *  @描述：
 */

public abstract class AbsModifyFragment extends BaseFragment {
    protected IModifyCallback mCallback;
    protected String mExtra;
    private WaitDialog mWaitDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IModifyCallback) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement IModifyCallback");
        }
    }
    @Override
    protected void initBundle(Bundle bundle) {
        mExtra = bundle.getString("extra");
    }
    /**
     *
     * @return todo 设置监听器, 统一activity处理结果
     */
    public abstract boolean save();

    protected void hideLoading(){
        if (mWaitDialog != null) {
            mWaitDialog.hide();
        }
    }
    protected void showLoading(){
        if (mWaitDialog == null) {
            mWaitDialog = new WaitDialog(mContext);
            mWaitDialog.setRemindTxt("");
        }
        mWaitDialog.show();
    }
    protected void saveUserInfo(UserEntity userEntity){
        SpUtil.getInstance(AppConst.Extra.USER_SP_NAME).put(AppConst.Extra.USER, JsonUtil.jsonString(userEntity));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
            mWaitDialog = null;
        }
    }
}
