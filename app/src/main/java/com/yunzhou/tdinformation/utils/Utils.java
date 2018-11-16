package com.yunzhou.tdinformation.utils;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   Utils
 *  @创建者:   lz
 *  @创建时间:  2018/9/27 11:12
 *  @描述：    app一些业务工具类
 */

import android.app.Activity;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yunzhou.common.utils.TDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public  static String addBasicParams(String base ,  Map<String ,String> map){
        if (map == null) {
            return base;
        }
        StringBuilder builder = new StringBuilder(base);
        int pos = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey().trim();
            String value = entry.getValue().trim();
            if (pos == 0) {
                builder.append("?");
            } else {
                builder.append("&");
            }
            builder.append(key).append("=").append(value);
            pos++;
        }
        return builder.toString();
    }
    /**
     * 删除空格和其它制表符
     * @param src
     * @return
     */
    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
    /**
     * 比较密码输入是否一致
     */
    public static boolean checkPasswordEqual(String firstNum , String secondNum) {
        if (TextUtils.isEmpty(firstNum) || TextUtils.isEmpty(secondNum)) {
            return false;
        } else {
            return firstNum.equals(secondNum);
        }
    }
    /**
     * 检查密码长度
     */
    public static boolean checkPasswordLength(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        } else {
            return src.length() >= 8 && src.length()<= 16;
        }
    }

    public static boolean checkAuth(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        } else {
            return src.length() == 4;
        }
    }

    public static boolean calculateShowCheckAllText(String content) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(TDevice.dip2px(16f));
        float textWidth = textPaint.measureText(content);
        float maxContentViewWidth = TDevice.getScreenWidth() - TDevice.dip2px(74f);
        float maxLines = textWidth / maxContentViewWidth;
        return maxLines > 4;
    }

    /**
     * 分割开奖号码
     * @param openCode 开奖号码字符串
     * @return 返回分割好的红球以及篮球
     */
    public static List<String> splitOpenCode(String openCode) {
        int redPos = openCode.indexOf("+");
        List<String> stringList = new ArrayList<>(2);
        String redCode;
        String blueCode = null;
        if (redPos != -1) {
            // 说明有蓝球
            redCode = openCode.substring(0, redPos);
            blueCode = openCode.substring(redPos + 1, openCode.length());
        } else {
            // 没有蓝球
            System.out.println(openCode);
            redCode = openCode;
        }
        if (redCode != null && redCode.length() != 0) {
            stringList.add(redCode);
        }
        if (blueCode != null && blueCode.length() != 0) {
            stringList.add(blueCode);
        }
        return stringList;
    }
    public static String[] getDimenFromUri(Uri uri) throws Exception{
        String str = uri.toString();
        int pos = str.lastIndexOf("_");
        String substring = str.substring(pos+1);
        int point = substring.indexOf(".");
        String result = substring.substring(0, point);
        return result.split("x");
    }
    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        /*//取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);*/
        //让view不根据系统窗口来调整自己的布局
        window.setStatusBarColor(statusColor);
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
}
