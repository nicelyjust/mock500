package com.yunzhou.tdinformation.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 *  @项目名：  Pad_VideoTraining
 *  @包名：    com.eebbk.vtraining.view
 *  @文件名:   ChildViewPager
 *  @创建者:   lz
 *  @创建时间:  2017/6/20 15:06
 *  @描述：    控制是否开启左右滑动
 */
public class NoScrollViewPager extends ViewPager {

	private boolean isScrollable ;

	public NoScrollViewPager(Context context) {
		super(context);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 关闭/开启 滑动功能
	 *
	 * @param isCanScroll
	 */
	public void setEnableScroll( boolean isCanScroll ) {
		this.isScrollable = isCanScroll;
	}
	/**
	 * 1.是否派发
	 * 原则:一般不做处理
	 * return true-->不派发
	 * return false-->不派发
	 *
	 * @param ev
	 * @return
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isScrollable && super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return isScrollable && super.onTouchEvent(ev);
	}
}