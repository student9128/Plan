package com.example.kevinjing.plan.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ProgressWaveView extends View implements View.OnClickListener {
    private Paint mPaint, mTextPaint;
    private int mColor, mBackgroundColor;
    private int textSize;
    private int textColor;
    private Canvas bitmapCanvas;
    private Bitmap bitmap;
    private int mDefaultWidth, mDefaultHeight;
    private int waveLength;
    private static final String TAG = "ProgressWaveView";
    private int viewWidth, viewHeight;
    private int waveCount;
    private int viewSize;
    private Path mPath;
    private float horizontalOffset;
    private int centerY;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int defaultPadding;
    private int mLeft, mTop, mRight, mBottom;
    private ValueAnimator animator;
    private int mR;
    private int mCurrentProgress;
    private int mTotalProgress;
    private float percent;

    public ProgressWaveView(Context context) {
        this(context, null);
    }

    public ProgressWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressWaveView);
        if (typedArray != null) {

        }
        mDefaultWidth = DisplayUtils.dp2px(context, 100);
        mDefaultHeight = DisplayUtils.dp2px(context, 100);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        defaultPadding = DisplayUtils.dp2px(getContext(), 5);
        bitmap = Bitmap.createBitmap(mDefaultWidth, mDefaultHeight, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);

        setOnClickListener(this);
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
                result = Math.min(result, specSize);
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
        Log.i(TAG, "left=" + left + ",\t top=" + top + ",\t right=" + right + ",\t bottom=" + bottom);
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
        Log.i(TAG, "mleft=" + mLeft + ",\t mtop=" + mTop + ",\t mright=" + mRight + ",\t mbottom=" + mBottom);
        mR = Math.min(getWidth() - paddingLeft - paddingRight, getHeight() - paddingTop - paddingBottom) / 2;
        Log.w(TAG, "mR==" + mR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "w=" + w + ",\t h=" + h);
        viewWidth = w;
        viewHeight = h;
        waveLength = Math.min(w, h);
        viewSize = Math.min(w, h);
        waveCount = (int) (Math.ceil(w / waveLength) + 1);
        Log.i(TAG, "waveCount=" + waveCount);
        Log.i(TAG, "viewSize=" + viewSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.RED);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(30);
        String x = (int) percent+"%";
        Rect bound = new Rect();
        mTextPaint.getTextBounds(x, 0, x.length(), bound);

        mPaint.setColor(Color.GREEN);
        centerY = (mTop + mBottom) / 2;
        int y = (int) (mTop + (mBottom - mTop) * (1 - percent/100));
        int centerX = (mLeft + mRight) / 2;
//        centerY = viewSize / 2;
        mPath.reset();
        mPath.moveTo(mLeft, y);
        for (int i = 0; i < waveCount; i++) {
            mPath.quadTo((-waveLength * 3 / 4) + i * waveLength + horizontalOffset, y + 30, (-waveLength / 2) + i * waveLength + horizontalOffset, y);
            mPath.quadTo((-waveLength / 4) + i * waveLength + horizontalOffset, y - 30, i * waveLength + horizontalOffset, y);

        }
        mPath.lineTo(viewSize, viewSize / 2);
        mPath.rLineTo(0, viewSize / 2);
        mPath.rLineTo(-viewSize, 0);
        mPath.close();
        canvas.save();
        canvas.translate(-mLeft + paddingLeft, -mTop + paddingTop);
        bitmapCanvas.drawCircle(centerX, centerY, mR, mPaint);
        int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, null);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setColor(Color.RED);
        bitmapCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);
        canvas.drawText(x, centerX - (bound.left + bound.right) / 2, centerY - (bound.top + bound.bottom) / 2, mTextPaint);
        canvas.restoreToCount(saveLayer);
        canvas.restore();
    }

    private void startAnimation() {
        animator = ValueAnimator.ofFloat(0, waveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                horizontalOffset = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
        ValueAnimator a = ValueAnimator.ofFloat(0, 80);
        a.setDuration(5000);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        a.start();
        if (percent >= 1) {
            if (a.isRunning()) {
                a.cancel();
            }
        }
    }

    @Override
    public void onClick(View v) {
        startAnimation();
    }
}
