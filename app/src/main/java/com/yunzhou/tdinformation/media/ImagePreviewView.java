package com.yunzhou.tdinformation.media;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * 支持图片预览, 放大,缩小,位置自适应,双击放大缩小
 * Created by thanatosx on 16/5/3.
 */
public class ImagePreviewView extends android.support.v7.widget.AppCompatImageView {

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mFlatDetector;

    private float scale = 1.f;
    private static final float mMaxScale = 4.f;
    private static final float mMinScale = 0.4f;
    private float translateLeft = 0.f;
    private float translateTop = 0.f;
    private int mBoundWidth = 0;
    private int mBoundHeight = 0;
    private boolean isAutoScale = false;

    private ValueAnimator resetScaleAnimator;
    private ValueAnimator resetXAnimator;
    private ValueAnimator resetYAnimator;
    private ValueAnimator.AnimatorUpdateListener onScaleAnimationUpdate;
    private ValueAnimator.AnimatorUpdateListener onTranslateXAnimationUpdate;
    private ValueAnimator.AnimatorUpdateListener onTranslateYAnimationUpdate;

    private FloatEvaluator mFloatEvaluator = new FloatEvaluator();
    private AccelerateInterpolator mAccInterpolator = new AccelerateInterpolator();
    private DecelerateInterpolator mDecInterpolator = new DecelerateInterpolator();

    private OnReachBorderListener onReachBorderListener;

    public interface OnReachBorderListener {
        void onReachBorder(boolean isReached);
    }

    public void setOnReachBorderListener(OnReachBorderListener l) {
        onReachBorderListener = l;
    }

    public ImagePreviewView(Context context) {
        this(context, null);
    }

    public ImagePreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mFlatDetector = new GestureDetector(getContext(), new FlatGestureListener());
    }

    /**
     * 重置伸缩动画的监听器
     *
     * @return
     */
    public ValueAnimator.AnimatorUpdateListener getOnScaleAnimationUpdate() {
        if (onScaleAnimationUpdate != null) return onScaleAnimationUpdate;
        onScaleAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        return onScaleAnimationUpdate;
    }

    /**
     * 重置水平动画的监听器
     *
     * @return
     */
    public ValueAnimator.AnimatorUpdateListener getOnTranslateXAnimationUpdate() {
        if (onTranslateXAnimationUpdate != null) return onTranslateXAnimationUpdate;
        onTranslateXAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        return onTranslateXAnimationUpdate;
    }

    /**
     * 重置垂直动画的监听器
     *
     * @return
     */
    public ValueAnimator.AnimatorUpdateListener getOnTranslateYAnimationUpdate() {
        if (onTranslateYAnimationUpdate != null) return onTranslateYAnimationUpdate;
        onTranslateYAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateTop = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        return onTranslateYAnimationUpdate;
    }

    /**
     * 重置伸缩
     *
     * @return the animator control scale value
     */
    private ValueAnimator getResetScaleAnimator() {
        if (resetScaleAnimator != null) {
            resetScaleAnimator.removeAllUpdateListeners();
        } else {
            resetScaleAnimator = ValueAnimator.ofFloat();
        }
        resetScaleAnimator.setDuration(150);
        resetScaleAnimator.setInterpolator(mAccInterpolator);
        resetScaleAnimator.setEvaluator(mFloatEvaluator);
        return resetScaleAnimator;
    }

    /**
     * 水平方向的重置动画
     *
     * @return
     */
    private ValueAnimator getResetXAnimator() {
        if (resetXAnimator != null) {
            resetXAnimator.removeAllUpdateListeners();
        } else {
            resetXAnimator = ValueAnimator.ofFloat();
        }
        resetXAnimator.setDuration(150);
        resetXAnimator.setInterpolator(mAccInterpolator);
        resetXAnimator.setEvaluator(mFloatEvaluator);
        return resetXAnimator;
    }

    /**
     * 垂直方向的重置动画
     *
     * @return
     */
    private ValueAnimator getResetYAnimator() {
        if (resetYAnimator != null) {
            resetYAnimator.removeAllUpdateListeners();
        } else {
            resetYAnimator = ValueAnimator.ofFloat();
        }
        resetYAnimator.setDuration(150);
        resetYAnimator.setInterpolator(mAccInterpolator);
        resetYAnimator.setEvaluator(mFloatEvaluator);
        return resetYAnimator;
    }

    private void cancelAnimation() {
        if (resetScaleAnimator != null && resetScaleAnimator.isRunning()) {
            resetScaleAnimator.cancel();
        }
        if (resetXAnimator != null && resetXAnimator.isRunning()) {
            resetXAnimator.cancel();
        }
        if (resetYAnimator != null && resetYAnimator.isRunning()) {
            resetYAnimator.cancel();
        }
    }

    /**
     * @return 如果是正数, 左边有空隙, 如果是负数, 右边有空隙, 如果是0, 代表两边都没有空隙
     */
    private float getDiffX() {
        final float mScaledWidth = mBoundWidth * scale;
        return translateLeft >= 0
                ? translateLeft
                : getWidth() - translateLeft - mScaledWidth > 0
                ? -(getWidth() - translateLeft - mScaledWidth)
                : 0;
    }

    /**
     * @return 如果是正数, 上面有空隙, 如果是负数, 下面有空隙, 如果是0, 代表两边都没有空隙
     */
    private float getDiffY() {
        final float mScaledHeight = mBoundHeight * scale;
        return translateTop >= 0
                ? translateTop
                : getHeight() - translateTop - mScaledHeight > 0
                ? -(getHeight() - translateTop - mScaledHeight)
                : 0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        // 清理动画
        if (action == MotionEvent.ACTION_DOWN) {
            cancelAnimation();
        }

        mFlatDetector.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if (isAutoScale) {
                isAutoScale = false;
            } else {
                if (scale < 1) {
                    ValueAnimator animator = getResetScaleAnimator();
                    animator.setFloatValues(scale, 1.f);
                    animator.addUpdateListener(getOnScaleAnimationUpdate());
                    animator.start();
                }
                final float mScaledWidth = mBoundWidth * scale;
                final float mScaledHeight = mBoundHeight * scale;

                final float mDiffX = getDiffX();
                final float mDiffY = getDiffY();

                // 左右边界重置
                if (mScaledWidth >= getWidth() && mDiffX != 0) {
                    ValueAnimator animator = getResetXAnimator();
                    animator.setFloatValues(translateLeft, translateLeft - mDiffX);
                    animator.addUpdateListener(getOnTranslateXAnimationUpdate());
                    animator.start();
                }

                // 上下边界重置
                if (mScaledHeight >= getHeight() && mDiffY != 0) {
                    ValueAnimator animator = getResetYAnimator();
                    animator.setFloatValues(translateTop, translateTop - mDiffY);
                    animator.addUpdateListener(getOnTranslateYAnimationUpdate());
                    animator.start();
                }

                // width重置到中间位置
                if (mScaledWidth < getWidth() && mScaledHeight >= getHeight() && mDiffX != 0) {
                    ValueAnimator animator = getResetXAnimator();
                    animator.setFloatValues(translateLeft, 0);   // 宽度总是填充的
                    animator.addUpdateListener(getOnTranslateXAnimationUpdate());
                    animator.start();
                }

                // height重置到中间位置
                if (mScaledHeight < getHeight() && mScaledWidth >= getWidth() && mDiffY != 0) {
                    ValueAnimator animator = getResetYAnimator();
                    animator.setFloatValues(translateTop, (getHeight() - mScaledHeight) / 2.f);
                    animator.addUpdateListener(getOnTranslateYAnimationUpdate());
                    animator.start();
                }

                if (mScaledWidth < getWidth() && mScaledHeight < getHeight()) {
                    resetDefaultState();
                }
            }
        }

        return true;
    }

    private void resetDefaultState() {
        if (translateLeft != 0) {
            ValueAnimator mTranslateXAnimator = getResetXAnimator();
            mTranslateXAnimator.setFloatValues(translateLeft, 0);
            mTranslateXAnimator.addUpdateListener(getOnTranslateXAnimationUpdate());
            mTranslateXAnimator.start();
        }

        ValueAnimator mTranslateYAnimator = getResetYAnimator();
        mTranslateYAnimator.setFloatValues(translateTop, getDefaultTranslateTop(getHeight(), mBoundHeight));
        mTranslateYAnimator.addUpdateListener(getOnTranslateYAnimationUpdate());
        mTranslateYAnimator.start();

    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        super.setFrame(l, t, r, b);

        Drawable drawable = getDrawable();
        if (drawable == null) return false;
        if (mBoundWidth != 0 && mBoundHeight != 0 && scale != 1) return false;

        adjustBounds(getWidth(), getHeight());

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        adjustBounds(w, h);
    }

    private void adjustBounds(int width, int height) {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        mBoundWidth = drawable.getBounds().width();
        mBoundHeight = drawable.getBounds().height();

        float scale = (float) mBoundWidth / width;

        mBoundHeight /= scale;
        mBoundWidth = width;

        drawable.setBounds(0, 0, mBoundWidth, mBoundHeight);

        translateLeft = 0;
        translateTop = getDefaultTranslateTop(height, mBoundHeight);
    }

    private float getDefaultTranslateTop(int height, int bh) {
        float top = (height - bh) / 2.f;
        return top > 0 ? top : 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        if (mDrawable == null) return;

        final int mDrawableWidth = mDrawable.getIntrinsicWidth();
        final int mDrawableHeight = mDrawable.getIntrinsicHeight();

        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return;     // nothing to draw (empty bounds)
        }

        int saveCount = canvas.getSaveCount();
        canvas.save();

        canvas.translate(translateLeft, translateTop);
        canvas.scale(scale, scale);

        // 如果先scale,再translate,那么,真实translate的值是要与scale值相乘的
        mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        /**
         * factor = detector.getCurrentSpan() / detector.getPreviousSpan()
         *
         * @param detector
         * @return
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            final float mOldScaledWidth = mBoundWidth * scale;
            final float mOldScaledHeight = mBoundHeight * scale;

            if (mOldScaledWidth > getWidth() && getDiffX() != 0 ||
                    (mOldScaledHeight > getHeight() && getDiffY() != 0)) return false;

            float factor = detector.getScaleFactor();
            float value = scale;
            value += (factor - 1) * 2;
            if (value == scale) return true;
            if (value <= mMinScale) return false;
            if (value > mMaxScale) return false;
            scale = value;
            final float mScaledWidth = mBoundWidth * scale;
            final float mScaledHeight = mBoundHeight * scale;

            // 走了些弯路, 不应该带入translateX计算, 因为二次放大之后计算就不正确了,它应该受scale的制约
            translateLeft = getWidth() / 2.f - (getWidth() / 2.f - translateLeft) * mScaledWidth / mOldScaledWidth;
            translateTop = getHeight() / 2.f - (getHeight() / 2.f - translateTop) * mScaledHeight / mOldScaledHeight;

            final float diffX = getDiffX();
            final float diffY = getDiffY();

            // 考虑宽图, 如果缩小的时候图片左边界到了屏幕左边界,停留在左边界缩小
            if (diffX > 0 && mScaledWidth > getWidth()) {
                translateLeft = 0;
            }
            // 右边界问题
            if (diffX < 0 && mScaledWidth > getWidth()) {
                translateLeft = getWidth() - mScaledWidth;
            }

            // 考虑到长图,上边界问题
            if (diffY > 0 && mScaledHeight > getHeight()) {
                translateTop = 0;
            }

            // 下边界问题
            if (diffY < 0 && mScaledHeight > getHeight()) {
                translateTop = getHeight() - mScaledHeight;
            }

            invalidate();
            return true;
        }

    }

    private float getExplicitTranslateLeft(float l) {
        final float mScaledWidth = mBoundWidth * scale;
        if (l > 0) {
            l = 0;
        }
        if (-l + getWidth() > mScaledWidth) {
            l = getWidth() - mScaledWidth;
        }
        return l;
    }

    private float getExplicitTranslateTop(float t) {
        final float mScaledHeight = mBoundHeight * scale;
        if (t > 0) {
            t = 0;
        }
        if (-t + getHeight() > mScaledHeight) {
            t = getHeight() - mScaledHeight;
        }
        return t;
    }

    private class FlatGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * @param e1        horizontal event
         * @param e2        vertical event
         * @param distanceX previous X - current X, toward left , is position
         * @param distanceY previous Y - current Y, toward up, is position
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            final float mScaledWidth = mBoundWidth * scale;
            final float mScaledHeight = mBoundHeight * scale;

            if (mScaledHeight > getHeight()) {
                translateTop -= distanceY * 1.5;
                translateTop = getExplicitTranslateTop(translateTop);
                /*if (getDiffY() != 0) {
                    final float disY = (float) (Math.acos(Math.abs(getDiffY()) / getPanelHeight() * 6) * distanceY);
                    if (disY == disY) translateTop -= disY; // float 低值溢出变Nan数值
                } else {
                    translateTop -= distanceY * 1.5;
                }*/
            }

            boolean isReachBorder = false;
            if (mScaledWidth > getWidth()) {
                translateLeft -= distanceX * 1.5;
                final float t = getExplicitTranslateLeft(translateLeft);
                if (t != translateLeft) isReachBorder = true;
                translateLeft = t;
                /*if (getDiffX() != 0) {
                    final float disX = (float) (Math.acos(Math.abs(getDiffX()) / getWidth() * 4) * distanceX);
                    if (disX == disX) translateLeft -= disX;
                } else {
                    translateLeft -= distanceX * 1.5;
                }*/
            } else {
                isReachBorder = true;
            }

            if (onReachBorderListener != null)
                onReachBorderListener.onReachBorder(isReachBorder);

            invalidate();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return performClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mBoundWidth * scale > getWidth()) {
                float sx = translateLeft + (1f / 2f) * velocityX * 0.5f * 0.5f;
                sx = getExplicitTranslateLeft(sx);
                ValueAnimator mResetXAnimator = getResetXAnimator();
                mResetXAnimator.setDuration(300);
                mResetXAnimator.setInterpolator(mDecInterpolator);
                mResetXAnimator.setFloatValues(translateLeft, sx);
                mResetXAnimator.addUpdateListener(getOnTranslateXAnimationUpdate());
                mResetXAnimator.start();
            }

            if (mBoundHeight * scale > getHeight()) {
                float sy = translateTop + (1f / 2f) * velocityY * 0.5f * 0.5f;
                sy = getExplicitTranslateTop(sy);
                ValueAnimator mResetYAnimator = getResetYAnimator();
                mResetYAnimator.setDuration(300);
                mResetYAnimator.setInterpolator(mDecInterpolator);
                mResetYAnimator.setFloatValues(translateTop, sy);
                mResetYAnimator.addUpdateListener(getOnTranslateYAnimationUpdate());
                mResetYAnimator.start();
            }

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isAutoScale = true;
            ValueAnimator mResetScaleAnimator = getResetScaleAnimator();

            if (scale == 1.f) {
                mResetScaleAnimator.setFloatValues(1.f, 2.f);

                ValueAnimator mResetXAnimator = getResetXAnimator();
                ValueAnimator mResetYAnimator = getResetYAnimator();
                mResetXAnimator.setFloatValues(translateLeft, (getWidth() - mBoundWidth * 2.f) / 2.f);
                mResetYAnimator.setFloatValues(translateTop, getDefaultTranslateTop(getHeight(), mBoundHeight * 2));
                mResetXAnimator.addUpdateListener(getOnTranslateXAnimationUpdate());
                mResetYAnimator.addUpdateListener(getOnTranslateYAnimationUpdate());
                mResetXAnimator.start();
                mResetYAnimator.start();
            } else {
                mResetScaleAnimator.setFloatValues(scale, 1.f);
                resetDefaultState();
            }

            mResetScaleAnimator.addUpdateListener(getOnScaleAnimationUpdate());
            mResetScaleAnimator.start();
            return true;
        }
    }


}
