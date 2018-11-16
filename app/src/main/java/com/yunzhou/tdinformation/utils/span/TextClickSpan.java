package com.yunzhou.tdinformation.utils.span;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yunzhou.tdinformation.R;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   TextClickSpan
 *  @创建者:   lz
 *  @创建时间:  2018/10/12 9:49
 *  @描述：
 */
public class TextClickSpan extends ClickableSpan {

    private Context mContext;

    private boolean mPressed;

    public TextClickSpan(Context context) {
        this.mContext = context;
    }

    public void setPressed(boolean isPressed) {
        this.mPressed = isPressed;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.bgColor = mPressed ? ContextCompat.getColor(mContext, R.color.base_B5B5B5) : Color.TRANSPARENT;
        ds.setColor(ContextCompat.getColor(mContext, R.color.base_697A9F));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        // Toast.makeText(mContext, "You Click " + mUserName, Toast.LENGTH_SHORT).show();
    }
}
