package com.example.kevinjing.plan.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.kevinjing.plan.R;

/**
 * Created by Kevin on 2018/11/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class TestView extends View {
    private Paint mPaint;
    private int color;
    private int width, height;
    private static final String TAG = "TestView";

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        if (typedArray != null) {
            color = typedArray.getColor(R.styleable.TestView_testBackgroundColor, Color.BLUE);
            typedArray.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(color);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        RectF rectF = new RectF(5, 5, width - 5, height - 5);
        RectF rect = new RectF(0, 0, width, height);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        canvas.drawRoundRect(rect, 100, 100, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GREEN);

//        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速，不然shadow不生效
//        mPaint.setShadowLayer(5, 2, 2, Color.CYAN);
        canvas.drawRoundRect(rectF, 100, 100, mPaint);
        Path path = new Path();
        path.moveTo(0, 100);
        path.lineTo(300, 100);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        canvas.drawPath(path, mPaint);
//        RectF rectF = new RectF(50, 20, width - 50, height - 50);
//        canvas.drawRect(rectF, mPaint);


    }

    private int measureWidth(int measureSpec) {
        int result = 600;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        Log.i(TAG, "mode:\t" + mode);
        Log.i(TAG, "size:\t" + size);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
                ;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 200;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
                break;
        }
        return result;
    }
}
