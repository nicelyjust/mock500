package com.yunzhou.tdinformation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view
 *  @文件名:   RoundCornerImageView
 *  @创建者:   lz
 *  @创建时间:  2018/9/26 15:14
 *  @描述：
 */

public class RoundCornerImageView extends AppCompatImageView {
    public static  float cornerRadius;

    public RoundCornerImageView(Context context) {
        this(context ,null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
        cornerRadius = a.getFloat(R.styleable.RoundCornerImageView_round_radius, TDevice.dip2px(6));
        a.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        bitmap = GetRoundedCornerBitmap(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            //final float roundPx = 30;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
