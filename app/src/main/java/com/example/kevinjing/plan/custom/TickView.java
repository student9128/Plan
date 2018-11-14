package com.example.kevinjing.plan.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class TickView extends View {
    private float progress = 0;
    private int line1X, line1Y, line2X, line2Y;

    public TickView(Context context) {
        this(context, null);
    }

    public TickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 90);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(6000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                progress = (float) animation.getAnimatedValue();
                Log.i("TickView", "progress--"+progress);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
//        if (progress < 100) {
//            progress += 2;
//        }
        RectF rectF = new RectF(100, 100, 400, 400);
        canvas.drawArc(rectF, -45, 360 * progress / 100, false, paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(DisplayUtils.sp2px(getContext(), 20));
        Rect bound = new Rect();
        String x = (int)progress+ "%";
        paint.getTextBounds(x, 0, x.length(), bound);

        canvas.drawText(x, 250 - (bound.left + bound.right) / 2, 250 - (bound.bottom +
                bound.top) / 2, paint);

//        paint.setStrokeWidth(10);
//        if (progress >= 100) {
//            if (175 + line1X < 250) {
//                line1X += 2;
//                line1Y += 2;
//            }
//        }
//        canvas.drawLine(175, 250, 175 + line1X, 250 + line1Y, paint);
////
//        if (line1X >= 75) {
//            canvas.drawLine(175 + line1X, 250 + line1Y, 175 + line1X + line2X, 250 + line1Y -
//                    line2Y, paint);
//            line2X += 2;
//            line2Y += 3;
//        }
//        if (line2X < 100) {
//            postInvalidateDelayed(6);
//        }


    }
}
