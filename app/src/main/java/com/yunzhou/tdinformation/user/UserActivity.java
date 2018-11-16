package com.yunzhou.tdinformation.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.JsonBean;
import com.yunzhou.tdinformation.bean.blog.UploadImgEntity;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.media.SelectImageActivity;
import com.yunzhou.tdinformation.media.config.SelectOptions;
import com.yunzhou.tdinformation.mine.widget.SettingItem;
import com.yunzhou.tdinformation.user.modify.ModifyCommonActivity;
import com.yunzhou.tdinformation.view.WaitDialog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   UserActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/31 19:49
 *  @描述：    个人信息界面
 */

public class UserActivity extends BaseActivity<UserPresenter> implements View.OnClickListener, UserView {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.shadow)
    View mShadowView;
    @BindView(R.id.rl_setting_head)
    RelativeLayout mRlSettingHead;
    @BindView(R.id.iv_cur_head)
    ImageView mCurHead;
    @BindView(R.id.si_nickname)
    SettingItem mSiNickname;
    @BindView(R.id.si_sign)
    SettingItem mSiSign;
    @BindView(R.id.si_sex)
    SettingItem mSiSex;
    @BindView(R.id.si_city)
    SettingItem mSiCity;
    private UserPresenter mPresenter;
    private WaitDialog mDialog;
    private MyHandler mHandler;
    private static final int MSG_SHOW_CITY = 0x01;
    private static final int MSG_SHOW_CITY_ERROR = 0x02;
    private File mCacheFile;

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    protected UserPresenter createP(Context context) {
        mPresenter = new UserPresenter();
        return mPresenter;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("个人信息");
        mShadowView.setVisibility(View.GONE);

        UserEntity userEntity = UserManager.getInstance().getUserEntity();
        Glide.with(this).load(userEntity.getAvatar()).apply(new RequestOptions().skipMemoryCache(true).placeholder(R.mipmap.default_head).error(R.mipmap.default_head).centerCrop()).into(mCurHead);
        mSiNickname.setShowText(TextUtils.isEmpty(userEntity.getNickName()) ? "未设置" : userEntity.getNickName());
        mSiSign.setShowText(TextUtils.isEmpty(userEntity.getIntro()) ? "未设置" : userEntity.getIntro());
        if (userEntity.getGender() == 0) {
            mSiSex.setShowText("未设置");
        } else {
            mSiSex.setShowText( userEntity.getGender() == 2 ? "女" : "男");
        }
        mSiCity.setShowText(TextUtils.isEmpty(userEntity.getCity()) ? "未设置" : userEntity.getCity());
        mRlSettingHead.setOnClickListener(this);
        mSiNickname.setOnClickListener(this);
        mSiSign.setOnClickListener(this);
        mSiSex.setOnClickListener(this);
        mSiCity.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mPresenter.parseProvince(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_head:
                SelectImageActivity.show(this, new SelectOptions.Builder()
                        .setSelectCount(1)
                        .setHasCam(true)
                        .setCrop(700, 700)
                        .setCallback(new SelectOptions.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                                String path = images[0];
                                Glide.with(UserActivity.this).load("file://" + path).apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.mipmap.default_head).centerCrop()).into(mCurHead);
                                uploadNewPhoto(new File(path));
                            }
                        }).build());
                break;
            case R.id.si_nickname:
                ModifyCommonActivity.startForResult(this, ModifyCommonActivity.TYPE_NICKNAME);
                break;
            case R.id.si_sign:
                ModifyCommonActivity.startForResult(this, ModifyCommonActivity.TYPE_SIGN);
                break;
            case R.id.si_sex:
                ModifyCommonActivity.startForResult(this, ModifyCommonActivity.TYPE_GENDER);
                break;
            case R.id.si_city:
                if (mHandler == null) {
                    mHandler = new MyHandler(this);
                }
                if (mPresenter.isLoading())
                    return;

                if (mPresenter.hasProvinceData()) {
                    showPickerView(mPresenter.getOptions1Items(), mPresenter.getOptions2Items(), mPresenter.getOptions3Items());
                } else {
                    showLoading("数据解析中...");
                    mPresenter.parseProvince(false);
                }
                break;
            default:

                break;
        }
    }

    private void uploadNewPhoto(File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            ToastUtil.showShort(this, getString(R.string.title_icon_null));
        } else {
            this.mCacheFile = file;
            mPresenter.upHead(file);
        }
    }

    private void showPickerView(final List<JsonBean> options1Items, final ArrayList<ArrayList<String>> options2Items,
                                final ArrayList<ArrayList<ArrayList<String>>> options3Items) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2);
                String tx;
                if (province.equals(city)) {
                    tx = province + options3Items.get(options1).get(options2).get(options3);
                } else {
                    tx = province + city + options3Items.get(options1).get(options2).get(options3);
                }
                mPresenter.modifyCity(UserActivity.this, tx);
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setSubmitColor(Color.parseColor("#DD262C"))
                .setCancelColor(Color.parseColor("#DD262C"))
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new WaitDialog(this);
        }
        mDialog.show();
    }

    @Override
    public void showLoading(String tip) {
        if (mDialog == null) {
            mDialog = new WaitDialog(this);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.setRemindTxt(tip);
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, UserActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void showCity() {
        Message message = new Message();
        message.what = MSG_SHOW_CITY;
        mHandler.sendMessage(message);
    }

    @Override
    public void showCityError() {
        Message message = new Message();
        message.what = MSG_SHOW_CITY_ERROR;
        mHandler.sendMessage(message);
    }

    @Override
    public void showModifyNickName(String extra) {
        mSiNickname.setShowText(extra);
    }

    @Override
    public void showModifySign(String extra) {
        if (extra.length() > 12) {
            mSiSign.setShowText(extra.substring(0, 11) + "...");
        } else {
            mSiSign.setShowText(extra);
        }

    }

    @Override
    public void showModifyGender(String extra) {
        mSiSex.setShowText(extra.equals("2") ? "女" : "男");
    }

    @Override
    public void showModifyCity(String newCity) {
        mSiCity.setShowText(newCity);
    }

    @Override
    public void showModifyCityError() {
        ToastUtil.showShort(this, "修改失败,请重试");
    }

    @Override
    public void showModify(UploadImgEntity imgEntity) {
        mPresenter.saveHead(this, imgEntity.getOriginPath(), imgEntity.getThumbPath());
    }

    @Override
    public void deleteCacheImg() {
        if (mCacheFile != null) {
            File file = this.mCacheFile;
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void showModifyAvatarError() {
        ToastUtil.showShort(this, "修改头像失败,请重试");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.d("wtf" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            mPresenter.onActivityResult(requestCode, data);
    }

    private static class MyHandler extends Handler {
        private WeakReference<UserActivity> weakReference;

        public MyHandler(UserActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            UserActivity activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_SHOW_CITY:
                        activity.hideLoading();
                        activity.showPickerView(activity.mPresenter.getOptions1Items(),
                                activity.mPresenter.getOptions2Items(), activity.mPresenter.getOptions3Items());
                        break;
                    case MSG_SHOW_CITY_ERROR:
                        activity.hideLoading();
                        ToastUtil.showShort(activity, "数据解析失败,请重试");
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
