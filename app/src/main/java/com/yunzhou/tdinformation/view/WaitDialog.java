package com.yunzhou.tdinformation.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view
 *  @文件名:   WaitDialog
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 15:14
 *  @描述：    自定义等待的dialog
 */
public class WaitDialog extends Dialog {
    private static final String TAG = "WaitDialog";
    @BindView(R.id.dialog_wait_tv)
    TextView mTv;

    public WaitDialog(Context context) {
        super(context,R.style.Wait_Dialog);
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_wait);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //5.0 全透明实现
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

    }

    public void setRemindTxt(CharSequence text) {
        if (mTv != null) {
            mTv.setText(text);
        }
    }

    public void setRemindTxt(@StringRes int txtId) {
        if (mTv != null) {
            mTv.setText(txtId);
        }
    }

    public void setRemindTxtVisibility(int visibility) {
        if (mTv != null) {
            mTv.setVisibility(visibility);
        }
    }
}
