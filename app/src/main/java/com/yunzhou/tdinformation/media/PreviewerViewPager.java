package com.yunzhou.tdinformation.media;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 适配ImagePreviewerView的使用
 * Created by thanatos on 16/8/5.
 */
public class PreviewerViewPager extends ViewPager {

    private boolean isInterceptable = false;
    private boolean isTransition = false;
    private int mScrollState = SCROLL_STATE_IDLE;

    public PreviewerViewPager(Context context) {
        this(context, null);
    }

    public PreviewerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollState != SCROLL_STATE_IDLE) {
            return super.onInterceptTouchEvent(ev);
        }

        // 移动到边界改变拦截方式时
        if (isTransition) {
            int action = ev.getAction();
            ev.setAction(MotionEvent.ACTION_DOWN);
            super.onInterceptTouchEvent(ev);
            ev.setAction(action);
            isTransition = false;
        }

        boolean b = false;

        int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            isInterceptable = false;
        }

        if (action != MotionEvent.ACTION_MOVE || isInterceptable) {
            b = super.onInterceptTouchEvent(ev);
        }

        return isInterceptable && b;
    }

    public void isInterceptable(boolean b) {
        if (!isInterceptable && b) isTransition = true;
        this.isInterceptable = b;
    }

    private class PageChangeListener extends SimpleOnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }
    }
}
