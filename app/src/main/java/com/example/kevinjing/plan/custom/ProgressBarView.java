package com.example.kevinjing.plan.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ProgressBarView extends View {
    private int totalProgress;
    private float progress;
    private float mProgress;
    private Paint mPaint;
    private Paint mBackgroundPaint;
    private int mColor, mBackgroundBarColor;
    private float mProgressWith;
    private int mDefaultHeight;
    private int mDefaultWidth;
    private int mDefaultPadding;
    private ValueAnimator animator;
    private int textSize;
    private int mR;

    private int mLeft, mTop, mRight, mBottom;
    private static final String TAG = "ProgressBarView";
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int defaultPadding;
    private int defaultTextMarginLeft;//进度百分百距离进度条的距离
    private int mTextPosition;
    private static final int RIGHT = 0;
    private static final int INTER = 1;
    private static final int TOP = 2;

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        if (typedArray != null) {
            mBackgroundBarColor = typedArray.getColor(R.styleable.ProgressBarView_progressBarBackgroundColor, 0xaaf0f3f3);
            mColor = typedArray.getColor(R.styleable.ProgressBarView_progressBarColor, 0xff2f89fc);
            totalProgress = typedArray.getInt(R.styleable.ProgressBarView_progressBarTotalProgress, 100);
            mProgress = typedArray.getInt(R.styleable.ProgressBarView_progressBarProgress, 0);
            textSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBarView_progressBarTextSize, DisplayUtils.sp2px(getContext(), 14));
            mTextPosition = typedArray.getInt(R.styleable.ProgressBarView_position, RIGHT);
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        int screenHeight = dm.heightPixels;       // 屏幕高度（像素）
        defaultPadding = DisplayUtils.dp2px(getContext(), 5);
        mDefaultWidth = screenWidth * 3 / 4;//默认2/3屏幕
        mDefaultHeight = DisplayUtils.dip2px(getContext(), 20);
        defaultTextMarginLeft = DisplayUtils.dp2px(getContext(), 5);

        mR = DisplayUtils.dp2px(getContext(), 3);

        Log.d(TAG, "屏幕宽度（像素）：" + screenWidth);
        Log.d(TAG, "屏幕高度（像素）：" + screenHeight);

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


        mLeft = left + paddingLeft;
        mTop = top + paddingTop;
        mRight = right - paddingRight;
        mBottom = bottom - paddingBottom;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundBarColor);


        Rect bound = new Rect();
        String x = (int) progress + "%";
        mPaint.setTextSize(textSize);
        String total = totalProgress + "%";
        mPaint.getTextBounds(total, 0, total.length(), bound);
        int boundLeft = bound.left;
        int boundTop = bound.top;
        int boundRight = bound.right;
        int boundBottom = bound.bottom;
        int progressBarTotalWidth = getWidth() - paddingLeft - paddingRight;

        switch (mTextPosition) {
            case RIGHT:
                drawRightProgress(canvas, bound, x, boundTop, boundBottom, progressBarTotalWidth);
                break;
            case INTER:
                canvas.save();
                int progressBarWidth = progressBarTotalWidth;
                int centerY = (mTop + mBottom) / 2;
                float textLeft = mLeft + (progress / (totalProgress * 2) + 1 / 4) * progressBarWidth;
                RectF rectF = new RectF(mLeft, mTop, progress / totalProgress * progressBarWidth, mBottom);
                RectF rectB = new RectF(mLeft, mTop, mLeft + progressBarWidth, mBottom);
                RectF rectT = new RectF(textLeft-5, mTop, textLeft + bound.width()+5, mBottom);
                canvas.translate(-mLeft + paddingLeft, -mTop + paddingTop);
                canvas.drawRoundRect(rectB, mR, mR, mBackgroundPaint);
                canvas.drawRoundRect(rectF, mR, mR, mPaint);
//                canvas.drawText(x, mLeft + progress / totalProgress * progressBarWidth * 1 / 2+1/4*progressBarWidth, centerY - (boundTop + boundBottom) / 2, mPaint);
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(rectT, mPaint);
                mPaint.setColor(mColor);
                canvas.drawText(x, textLeft, centerY - (boundTop + boundBottom) / 2, mPaint);
                canvas.restore();
                break;
            case TOP:
                break;
        }


    }

    /**
     * text progress show right of the progress bar
     *
     * @param canvas
     * @param bound
     * @param x
     * @param boundTop
     * @param boundBottom
     * @param progressBarTotalWidth
     */
    private void drawRightProgress(Canvas canvas, Rect bound, String x, int boundTop, int boundBottom, int progressBarTotalWidth) {
        canvas.save();
        int progressBarWidth = progressBarTotalWidth - bound.width() - defaultTextMarginLeft;
        int centerY = (mTop + mBottom) / 2;
        RectF rectF = new RectF(mLeft, mTop, mLeft + progress / totalProgress * progressBarWidth, mBottom);
        RectF rectB = new RectF(mLeft, mTop, mLeft + progressBarWidth, mBottom);
        canvas.translate(-mLeft + paddingLeft, -mTop + paddingTop);
        canvas.drawRoundRect(rectB, mR, mR, mBackgroundPaint);
        canvas.drawRoundRect(rectF, mR, mR, mPaint);
        canvas.drawText(x, mLeft + progressBarWidth + defaultTextMarginLeft, centerY - (boundTop + boundBottom) / 2, mPaint);
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

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }

    public int getCurrentProgress() {
        return (int) mProgress;
    }

    public void setCurrentProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public int getmBackgroundBarColor() {
        return mBackgroundBarColor;
    }

    public void setmBackgroundBarColor(int mBackgroundBarColor) {
        this.mBackgroundBarColor = mBackgroundBarColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
