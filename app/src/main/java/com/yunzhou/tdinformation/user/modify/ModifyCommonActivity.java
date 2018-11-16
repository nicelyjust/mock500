package com.yunzhou.tdinformation.user.modify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.user.modify.base.AbsModifyFragment;
import com.yunzhou.tdinformation.user.modify.base.IModifyCallback;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.setting.EmailModifyFragment;

import butterknife.BindView;
import butterknife.OnClick;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   ModifyCommonActivity
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 17:33
 *  @描述：
 */

public class ModifyCommonActivity extends BaseCommonAct implements IModifyCallback {
    public static final int TYPE_NICKNAME = 1;
    public static final int TYPE_SIGN = 2;
    public static final int TYPE_GENDER = 3;
    public static final int TYPE_EMAIL = 4;

    @BindView(R.id.tv_cancel_modify)
    Button mTvCancel;
    @BindView(R.id.tv_finish_modify)
    Button mTvFinish;
    @BindView(R.id.tv_title_modify)
    TextView mTvTitle;
    private AbsModifyFragment mBaseFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_common;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        UserEntity userEntity = UserManager.getInstance().getUserEntity();
        int showType = getIntent().getIntExtra(AppConst.Extra.SHOW_TYPE, 0);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (showType) {
            case TYPE_NICKNAME:
                mTvTitle.setText("修改昵称");
                mBaseFragment = NickNameModifyFragment.newInstance(userEntity.getNickName());
                break;
            case TYPE_SIGN:
                mTvTitle.setText("修改简介");
                mBaseFragment = SignModifyFragment.newInstance(userEntity.getIntro());
                break;
            case TYPE_GENDER:
                mTvTitle.setText("修改性别");
                mBaseFragment = GenderModifyFragment.newInstance(String.valueOf(userEntity.getGender()));
                break;
            case TYPE_EMAIL:
                mTvTitle.setText("设置邮箱");
                mBaseFragment = EmailModifyFragment.newInstance(userEntity.getEmail());
                break;
            default:
                mTvTitle.setText("设置邮箱");
                mBaseFragment = EmailModifyFragment.newInstance(userEntity.getEmail());
                break;
        }
        fragmentTransaction.add(R.id.fl_container_modify, mBaseFragment, String.valueOf(showType));
        fragmentTransaction.commit();
    }

    public static void startForResult(Activity context, int showType) {
        Intent starter = new Intent(context, ModifyCommonActivity.class);
        starter.putExtra(AppConst.Extra.SHOW_TYPE, showType);
        int requestCode;
        switch (showType) {
            case TYPE_NICKNAME:
                requestCode = AppConst.Request.MODIFY_NICKNAME;
                break;
            case TYPE_SIGN:
                requestCode = AppConst.Request.MODIFY_SIGN;
                break;
            case TYPE_GENDER:
                requestCode = AppConst.Request.MODIFY_GENDER;
                break;
            case TYPE_EMAIL:
            default:
                requestCode = AppConst.Request.MODIFY_EMAIL;
                break;

        }
        context.startActivityForResult(starter , requestCode);
    }
    @OnClick({R.id.tv_cancel_modify, R.id.tv_finish_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_modify:
                finish();
                break;
            case R.id.tv_finish_modify:
                if (mBaseFragment.save()) {
                    //请求发出去了
                }
                break;
        }
    }

    @Override
    public void setFinishEnabled(boolean enabled) {
        mTvFinish.setEnabled(enabled);
    }
}
