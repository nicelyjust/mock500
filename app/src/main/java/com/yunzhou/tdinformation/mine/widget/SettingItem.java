package com.yunzhou.tdinformation.mine.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.widget
 *  @文件名:   SettingItem
 *  @创建者:   lz
 *  @创建时间:  2018/10/31 20:26
 *  @描述：
 */

public class SettingItem extends RelativeLayout {
    @BindView(R.id.tv_setting_name)
    TextView mTvSettingName;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.v_setting_line)
    View mVSettingLine;
    private boolean mIsShowLine;
    private int mShowTxtColor;
    private float mShowTxtSize;
    private CharSequence mShowText;
    private int mSettingTxtColor;
    private float mSettingTxtSize;
    private CharSequence mSettingText;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initView();
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
        initView();
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_setting_common_item, this);
        ButterKnife.bind(this, view);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItem);
        mSettingTxtColor = typedArray.getColor(R.styleable.SettingItem_settingTextColor, getResources().getColor(R.color.colorText3));
        mSettingTxtSize = typedArray.getDimension(R.styleable.SettingItem_settingTextSize, TDevice.sp2px(16));
        mSettingText = typedArray.getText(R.styleable.SettingItem_settingTxt);

        mShowTxtColor = typedArray.getColor(R.styleable.SettingItem_showTextColor, getResources().getColor(R.color.colorText9));
        mShowTxtSize = typedArray.getDimension(R.styleable.SettingItem_showTextSize, TDevice.sp2px(15));
        mShowText = typedArray.getText(R.styleable.SettingItem_showTxt);
        mIsShowLine = typedArray.getBoolean(R.styleable.SettingItem_isShowLine, true);
        typedArray.recycle();
    }

    private void initView() {
        mTvSettingName.setText(mSettingText);
        mTvSettingName.setTextColor(mSettingTxtColor);
        mTvSettingName.setTextSize(TypedValue.COMPLEX_UNIT_PX ,mSettingTxtSize);
        if (!TextUtils.isEmpty(mShowText))
            mTvShow.setText(mShowText);
        mTvShow.setTextColor(mShowTxtColor);
        mTvShow.setTextSize(TypedValue.COMPLEX_UNIT_PX ,mShowTxtSize);
        mVSettingLine.setVisibility(mIsShowLine ? VISIBLE : GONE);
    }
    public void setShowText(@StringRes int resId){
        mTvShow.setText(resId);
    }
    public void setShowText(CharSequence text){
        mTvShow.setText(text);
    }

}
