package com.yunzhou.common.http.dialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.yunzhou.common.utils.TDevice;


public class ToastUtils {

    private static Toast mToast = null;
    private static boolean flag = true;

    /*private控制不应该被实例化*/
    private ToastUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 取消Toast显示
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    //Created by luotao 针对性调用
    public static void showToastWithBorder(Context context, @StringRes int msg, int duration, int gravity, int xOffset, int yOffset) {
        if (flag) {
            if (mToast == null)
                mToast = Toast.makeText(context, "", duration);
            mToast.setText(msg);
            mToast.setGravity(gravity, xOffset, yOffset);
            /*View toastView = mToast.getView();

            //((TextView)toastView.findViewById(com.android.internal.R.id.message)).setGravity(Gravity.CENTER);

            toastView.setBackgroundResource(R.drawable.toast_base);*/
            mToast.show();
        }
    }

    public static void showToastWithBorder(Context context, String msg, int duration, int gravity, int xOffset, int yOffset) {
        if (flag) {
            cancelToast();
            mToast = Toast.makeText(context, "", duration);
            mToast.setText(msg);
            mToast.setGravity(gravity, xOffset, yOffset);
            /*View toastView = mToast.getView();
            // ((TextView)toastView.findViewById(com.android.internal.R.id.message)).setGravity(Gravity.CENTER);
            toastView.setBackgroundResource(R.drawable.toast_base);*/
            mToast.show();
        }
    }

    public static void showToastWithBorder(Context context, @StringRes int msg, int duration, int gravity) {
        showToastWithBorder(context, msg, duration, gravity, 0,  TDevice.dip2px( 30));
    }
    public static void showToastWithBorder(Context context, String msg, int duration, int gravity) {
        showToastWithBorder(context, msg, duration, gravity, 0,  TDevice.dip2px( 30));
    }
}
