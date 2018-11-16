package com.yunzhou.tdinformation.setting;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   SettingAccountFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:46
 *  @描述：
 */

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.login.LoginFragmentFactory;
import com.yunzhou.tdinformation.user.modify.ModifyCommonActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.mine.widget.SettingItem;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingAccountFragment extends AbsSettingFragment {
    @BindView(R.id.si_bind_phone)
    SettingItem mSiBindPhone;
    @BindView(R.id.si_modify_psd)
    SettingItem mSiModifyPsd;
    @BindView(R.id.si_modify_pay_psd)
    SettingItem mSiModifyPayPsd;
    @BindView(R.id.si_auth_id)
    SettingItem mSiAuthId;
    @BindView(R.id.si_bind_bank)
    SettingItem mSiBindBank;
    @BindView(R.id.si_bind_email)
    SettingItem mSiBindEmail;

    @Override
    public String getTitle() {
        return "账户和安全";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_account;
    }

    @Override
    protected void initWidget(View root) {
        UserEntity entity = UserManager.getInstance().getUserEntity();
        String phone = entity.getPhone();
        mSiBindPhone.setShowText(TextUtils.isEmpty(phone) ? "未设置" : "*******" + phone.substring(7));

    }

    @OnClick({R.id.si_bind_phone, R.id.si_modify_psd, R.id.si_modify_pay_psd, R.id.si_auth_id, R.id.si_bind_bank, R.id.si_bind_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.si_bind_phone:
                PhoneBindingActivity.start(mContext);
                break;
            case R.id.si_modify_psd:
                LoginActivity.startModify(mContext , LoginFragmentFactory.ForgetTag);
                break;
            case R.id.si_modify_pay_psd:
                PayPsdModifyActivity.startForResult(getActivity());
                break;
            case R.id.si_auth_id:
                break;
            case R.id.si_bind_bank:
                break;
           case R.id.si_bind_email:
                ModifyCommonActivity. startForResult(getActivity() ,ModifyCommonActivity.TYPE_EMAIL);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d("requestCode == " + requestCode +";resultCode == " + resultCode);
    }
}
