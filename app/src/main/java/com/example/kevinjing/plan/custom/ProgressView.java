package com.example.kevinjing.plan.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/19<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ProgressView extends View {
    private int mProgress;
    private float progress;
    private Paint mPaint;
    private Paint mBackgroundPaint;
    private int mColor, mBackgroundCircleColor;
    private float mProgressWith;
    private int mDefaultHeight;
    private int mDefaultWidth;
    private int mDefaultPadding;
    private ValueAnimator animator;
    private int textSize;
    private int mR;
    /**
     * 顺时针还是逆时针，默认false,逆时针
     */
    private boolean clockWise;//顺时针还是逆时针，默认false,逆时针

//    private enum ClockWise {
//        CLOCK_WISE, ANTI_CLOCK_WISE
//    }

    private int mLeft, mTop, mRight, mBottom;
    private static final String TAG = "ProgressView";
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int defaultPadding;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        if (typedArray != null) {
            mBackgroundCircleColor = typedArray.getColor(R.styleable.ProgressView_progressBackgroundColor, 0xaaf0f3f3);
            mColor = typedArray.getColor(R.styleable.ProgressView_progressColor, 0xff2f89fc);
            mProgress = typedArray.getInt(R.styleable.ProgressView_progress, 0);
            textSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressTextSize, DisplayUtils.sp2px(getContext(), 14));
            clockWise = typedArray.getBoolean(R.styleable.ProgressView_clockWise, false);
            mProgressWith = typedArray.getDimension(R.styleable.ProgressView_progressWidth, DisplayUtils.dip2px(getContext(), 2));
            typedArray.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        defaultPadding = DisplayUtils.dp2px(getContext(), 3);
        mDefaultWidth = DisplayUtils.dip2px(getContext(), 60);
        mDefaultHeight = DisplayUtils.dip2px(getContext(), 60);

        animator = ValueAnimator.ofFloat(0, mProgress);
        startAnimation();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = mDefaultWidth;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = mDefaultHeight;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Log.i(TAG, "width:\t" + width + ",\theight:\t" + height);
        Log.i(TAG, "measuredWidth:\t" + measuredWidth + ",\tmeasuredHeight:\t" + measuredHeight);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();

        if (paddingLeft <= defaultPadding) {
            paddingLeft = defaultPadding;
        }
        if (paddingRight <= defaultPadding) {
            paddingRight = defaultPadding;
        }
        if (paddingBottom <= defaultPadding) {
            paddingBottom = defaultPadding;
        }
        if (paddingTop <= defaultPadding) {
            paddingTop = defaultPadding;
        }

        Log.i(TAG, "paddingLeft:\t" + paddingLeft + ",\tpaddingRight:\t" +
                paddingRight + "\tpaddingTop\t" + paddingTop + "\tpaddingBottom\t" + paddingBottom);


        mLeft = left + paddingLeft;
        mTop = top + paddingTop;
        mRight = right - paddingRight;
        mBottom = bottom - paddingBottom;
        mR = Math.min(getWidth() - paddingLeft - paddingRight, getHeight() - paddingTop - paddingBottom) / 2;
        Log.w(TAG, "left:\t" + mLeft + ",\tright:\t" + mRight + ",\ttop:\t" + mTop + "," +
                "\tbottom:\t" +
                mBottom);
        Log.w(TAG, "R=" + mR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mProgressWith);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setColor(mBackgroundCircleColor);
        mBackgroundPaint.setStrokeWidth(mProgressWith);

        int centerX = (mLeft + mRight) / 2;
        int centerY = (mTop + mBottom) / 2;
        Log.i(TAG, "centerX=" + centerX);
        Log.i(TAG, "centerY=" + centerY);
        RectF rectF = new RectF(mLeft, mTop, mRight, mBottom);
        Rect bound = new Rect();
        String x = (int) progress + "%";
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(x, 0, x.length(), bound);//获取bound后在平移画布，否则不居中
//translate canvas to (0,0),otherwise (0,0) just relative to the canvas
// the progressView can not be shown
        canvas.save();
        canvas.translate(-mLeft + paddingLeft, -mTop + paddingTop);
        canvas.drawCircle(centerX, centerY, mR, mBackgroundPaint);
        if (clockWise) {
            canvas.drawArc(rectF, -90, -360 * progress / 100, false, mPaint);
        } else {
            canvas.drawArc(rectF, 0, 360 * progress / 100, false, mPaint);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(x, centerX - (bound.left + bound.right) / 2, centerY - (bound.top + bound.bottom) / 2, mPaint);
//        canvas.translate(mLeft - paddingLeft, mTop - paddingTop);
        canvas.restore();
    }

    private void startAnimation() {
        animator.setDuration(5000)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();//
                invalidate();
            }
        });
        animator.start();
        if (progress >= mProgress) {
            progress = mProgress;
            cancelAnimation();
        }
    }

    private void cancelAnimation() {
        if (animator.isRunning()) {
            animator.cancel();
        }
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public int getBackgroundCircleColor() {
        return mBackgroundCircleColor;
    }

    public void setBackgroundCircleColor(int mBackgroundCircleColor) {
        this.mBackgroundCircleColor = mBackgroundCircleColor;
    }

    public float getProgressWith() {
        return mProgressWith;
    }

    public void setProgressWith(float mProgressWith) {
        this.mProgressWith = mProgressWith;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = DisplayUtils.sp2px(getContext(), textSize);
    }

    public boolean isClockWise() {
        return clockWise;
    }

    public void setClockWise(boolean clockWise) {
        this.clockWise = clockWise;
    }
}
