package com.yunzhou.tdinformation.mine.widget;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.yunzhou.tdinformation.R;


/**
 * toggle(),toggleOn ,toggleOff,setOnToggleChanged()
 */
public class ToggleButton extends View {
    /** */
    private float radius;
    /**
     * 开启颜色
     */
    private int onColor = Color.parseColor("#58C661");
    /**
     * 关闭颜色
     */
    private int offBorderColor = Color.parseColor("#dadbda");
    /**
     * 灰色带颜色
     */
    private int offColor = Color.parseColor("#E6E6E6");
    /**
     * 手柄颜色
     */
    private int spotColor = Color.parseColor("#ffffff");
    /**
     * 边框颜色
     */
    private int borderColor = offBorderColor;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 开关状态
     */
    private boolean toggleOn = false;
    /**
     * 边框大小
     */
    private int borderWidth = 1;
    /**
     * 垂直中心
     */
    private float centerY;
    /**
     * 按钮的开始和结束位置
     */
    private float startX, endX;
    /**
     * 手柄X位置的最小和最大值
     */
    private float spotMinX, spotMaxX;
    /**
     * 手柄大小
     */
    private int spotSize;
    /**
     * 手柄X位置
     */
    private float spotX;
    /**
     * 关闭时内部灰色带高度
     */
    private float offLineWidth;
    /** */
    private RectF rect = new RectF();

    private OnToggleChanged listener;

    private ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public void setup(AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Style.FILL);
        paint.setStrokeCap(Cap.ROUND);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                toggle();
            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleButton);
        offBorderColor = typedArray.getColor(R.styleable.ToggleButton_offBorderColor, offBorderColor);
        onColor = typedArray.getColor(R.styleable.ToggleButton_onColor, onColor);
        spotColor = typedArray.getColor(R.styleable.ToggleButton_spotColor, spotColor);
        offColor = typedArray.getColor(R.styleable.ToggleButton_offColor, offColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.ToggleButton_toggle_border_width, borderWidth);
        typedArray.recycle();
    }

    public void toggle() {
        toggleOn = !toggleOn;
        animateCheckedState(toggleOn);
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOn() {
        setToggleOn();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOff() {
        setToggleOff();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    /**
     * 设置显示成打开样式，不会触发toggle事件
     */
    public void setToggleOn() {
        toggleOn = true;
        setAnimatorProperty(true);
    }

    /**
     * 设置显示成关闭样式，不会触发toggle事件
     */
    public void setToggleOff() {
        toggleOn = false;
        setAnimatorProperty(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();
        radius = Math.min(width, height) * 0.5f;
        centerY = radius;
        startX = radius;
        endX = width - radius;
        spotMinX = startX + borderWidth;
        spotMaxX = endX - borderWidth;
        spotSize = height - 4 * borderWidth;

        // update values
        setAnimatorProperty(toggleOn);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (offLineWidth > 0) {
            final float cy = offLineWidth * 0.5f;
            rect.set(spotX - cy, centerY - cy, endX + cy, centerY + cy);
            paint.setColor(offColor);
            canvas.drawRoundRect(rect, cy, cy, paint);
        }

        rect.set(spotX - 1 - radius, centerY - radius, spotX + 1.1f + radius, centerY + radius);
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        final float spotR = spotSize * 0.5f;
        rect.set(spotX - spotR, centerY - spotR, spotX + spotR, centerY + spotR);
        paint.setColor(spotColor);
        canvas.drawRoundRect(rect, spotR, spotR, paint);
    }

    /**
     * =============================================================================================
     * The Animate
     * =============================================================================================
     */
    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    private static final int ANIMATION_DURATION = 280;
    private ObjectAnimator mAnimator;

    @SuppressLint("ObsoleteSdkInt")
    private void animateCheckedState(boolean newCheckedState) {
        AnimatorProperty property = new AnimatorProperty();
        if (newCheckedState) {
            property.color = onColor;
            property.offLineWidth = 10;
            property.spotX = spotMaxX;
        } else {
            property.color = offBorderColor;
            property.offLineWidth = spotSize;
            property.spotX = spotMinX;
        }

        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofObject(this, ANIM_VALUE, new AnimatorEvaluator(mCurProperty), property);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                mAnimator.setAutoCancel(true);
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.setInterpolator(ANIMATION_INTERPOLATOR);
        } else {
            mAnimator.cancel();
            mAnimator.setObjectValues(property);
        }
        mAnimator.start();
    }

    /**
     * =============================================================================================
     * The custom properties
     * =============================================================================================
     */

    private AnimatorProperty mCurProperty = new AnimatorProperty();

    private void setAnimatorProperty(AnimatorProperty property) {
        this.spotX = property.spotX;
        this.borderColor = property.color;
        this.offLineWidth = property.offLineWidth;
        invalidate();
    }

    private void setAnimatorProperty(boolean isOn) {
        AnimatorProperty property = mCurProperty;
        if (isOn) {
            property.color = onColor;
            property.offLineWidth = 10;
            property.spotX = spotMaxX;
        } else {
            property.color = offBorderColor;
            property.offLineWidth = spotSize;
            property.spotX = spotMinX;
        }
        setAnimatorProperty(property);
    }

    private final static class AnimatorProperty {
        private int color;
        private float offLineWidth;
        private float spotX;
    }

    private final static class AnimatorEvaluator implements TypeEvaluator<AnimatorProperty> {
        private final AnimatorProperty mProperty;

        public AnimatorEvaluator(AnimatorProperty property) {
            mProperty = property;
        }

        @Override
        public AnimatorProperty evaluate(float fraction, AnimatorProperty startValue, AnimatorProperty endValue) {
            // Values
            mProperty.spotX = (int) (startValue.spotX + (endValue.spotX - startValue.spotX) * fraction);

            mProperty.offLineWidth = (int) (startValue.offLineWidth + (endValue.offLineWidth - startValue.offLineWidth) * (1 - fraction));

            // Color
            int startA = (startValue.color >> 24) & 0xff;
            int startR = (startValue.color >> 16) & 0xff;
            int startG = (startValue.color >> 8) & 0xff;
            int startB = startValue.color & 0xff;

            int endA = (endValue.color >> 24) & 0xff;
            int endR = (endValue.color >> 16) & 0xff;
            int endG = (endValue.color >> 8) & 0xff;
            int endB = endValue.color & 0xff;

            mProperty.color = (startA + (int) (fraction * (endA - startA))) << 24 |
                    (startR + (int) (fraction * (endR - startR))) << 16 |
                    (startG + (int) (fraction * (endG - startG))) << 8 |
                    (startB + (int) (fraction * (endB - startB)));

            return mProperty;
        }
    }

    private final static Property<ToggleButton, AnimatorProperty> ANIM_VALUE = new Property<ToggleButton, AnimatorProperty>(AnimatorProperty.class, "animValue") {
        @Override
        public AnimatorProperty get(ToggleButton object) {
            return object.mCurProperty;
        }

        @Override
        public void set(ToggleButton object, AnimatorProperty value) {
            object.setAnimatorProperty(value);
        }
    };


    /**
     * @author ThinkPad
     */
    public interface OnToggleChanged {
        /**
         * @param on
         */
        public void onToggle(boolean on);
    }


    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }
}
