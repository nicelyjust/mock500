package com.yunzhou.tdinformation.view;

import android.app.Dialog;
import android.content.Context;

import com.yunzhou.tdinformation.R;

import butterknife.ButterKnife;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view
 *  @文件名:   WaitDialog
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 15:14
 *  @描述：    自定义等待的dialog
 */
public class WaitNoBgDialog extends Dialog {
    private static final String TAG = "WaitNoBgDialog";

    public WaitNoBgDialog(Context context) {
        super(context, R.style.Wait_Dialog);
        setContentView(R.layout.dialog_wait_no_bg);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
    }
}
