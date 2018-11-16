package com.yunzhou.common.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.UUID;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   TDevice
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:04
 *  @描述：
 */
public class TDevice {

	public static void hideSoftKeyboard(View view) {
		if (view == null) return;
		View mFocusView = view;

		Context context = view.getContext();
		if (context != null && context instanceof Activity) {
			Activity activity = ((Activity) context);
			mFocusView = activity.getCurrentFocus();
		}
		if (mFocusView == null) return;
		mFocusView.clearFocus();
		InputMethodManager manager = (InputMethodManager) mFocusView.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void showSoftKeyboard(View view) {
		if (view == null) return;
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		if (!view.isFocused()) view.requestFocus();

		InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, 0);
	}
	public static void closeKeyboard(EditText view) {
		view.clearFocus();
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 *
	 * @param pxValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip( float pxValue) {
		final float scale = UiUtils.getApp().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 *
	 * @param dipValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue) {
		final float scale = UiUtils.getApp().getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp( float pxValue) {
		final float fontScale = UiUtils.getApp().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px( float spValue) {
		final float fontScale = UiUtils.getApp().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	/*------------ 获得机器像素以及DPI -------------*/
	public static DisplayMetrics getDisplayMetrics() {
		return UiUtils.getApp().getResources().getDisplayMetrics();
	}

	public static float getScreenHeightPX() {
		return getDisplayMetrics().heightPixels;
	}

	public static float getScreenWidthPX() {
		return getDisplayMetrics().widthPixels;
	}

	public static float getScreenDPI() {
		return getDisplayMetrics().densityDpi;
	}

	public static boolean hasInternet() {
		ConnectivityManager cm = (ConnectivityManager) UiUtils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isAvailable() && info.isConnected();
	}

	/**
	 * 获得屏幕的宽度
	 * @return width
	 */
	public static int getScreenWidth() {
		WindowManager manager = (WindowManager) UiUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
		Display       display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 获得屏幕的高度
	 * @return height
	 */
	public static int getScreenHeight() {
		WindowManager manager = (WindowManager) UiUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	//获得独一无二的Psuedo ID
	public static String getUniquePsuedoID() {
		String serial = null;
		String m_szDevIDShort = "35" +
				Build.BOARD.length()%10+ Build.BRAND.length()%10 +

				Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +

				Build.DISPLAY.length()%10 + Build.HOST.length()%10 +

				Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +

				Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +

				Build.TAGS.length()%10 + Build.TYPE.length()%10 +

				Build.USER.length()%10 ; //13 位

		try {
			serial = android.os.Build.class.getField("SERIAL").get(null).toString();
			//API>=9 使用serial号
			return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
		} catch (Exception exception) {
			//serial需要一个初始化
			serial = "serial"; // 随便一个初始化
		}
		//使用硬件信息拼凑出来的15位号码
		return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
	}
}
