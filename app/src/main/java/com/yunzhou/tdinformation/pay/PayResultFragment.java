package com.yunzhou.tdinformation.pay;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay
 *  @文件名:   PayFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/30 10:00
 *  @描述：
 */

public class PayResultFragment extends DialogFragment implements View.OnClickListener {

    private TextView mDealine;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_result);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度持平
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        initBundle(getArguments());
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        mDealine = dialog.findViewById(R.id.tv_deadline);
        dialog.findViewById(R.id.tv_know).setOnClickListener(this);
    }
    private void initBundle(Bundle bundle) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_know:
                dismiss();
                break;

        }
    }

    public static PayResultFragment newInstance() {
        // Bundle args = new Bundle();
        PayResultFragment fragment = new PayResultFragment();
        // fragment.setArguments(args);
        return fragment;
    }
}
