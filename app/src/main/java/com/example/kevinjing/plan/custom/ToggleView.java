package com.example.kevinjing.plan.custom;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/12<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ToggleView extends View {
    private int mDefaultHeight;
    private int mDefaultWidth;
    private int mBackgroundColor;
    private int mToggleColor;
    private int mBorderColor = 0xffcacaca, mmBorderColor = 0xffcacaca;
    private Paint mBorderPaint, mTogglePaint, mToggleBorderPaint, mBackgroundPaint;
    private static final String TAG = "ToggleView";
    /**
     * canvas's l,t,r,b
     */
    private int mLeft, mTop, mRight, mBottom;
    /**
     * radius
     */
    private int mR;

    private int mCheckedColor;
    private int mUnCheckedColor;
    private boolean mChecked = false;
    private int mBorderWidth;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int defaultPadding;
    private float fraction;
    private ValueAnimator animator;


    public ToggleView(Context context) {
        this(context, null);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mTogglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mToggleBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleView);
        if (typedArray != null) {
            mBackgroundColor = typedArray.getColor(R.styleable.ToggleView_toggleBackgroundColor, 0xffffffff);
            mBorderColor = typedArray.getColor(R.styleable.ToggleView_borderColor, 0xffcacaca);
            mCheckedColor = typedArray.getColor(R.styleable.ToggleView_checkedColor, 0xff65eeb7);
            mUnCheckedColor = typedArray.getColor(R.styleable.ToggleView_unCheckedColor, 0xffffffff);
            mToggleColor = typedArray.getColor(R.styleable.ToggleView_toggleColor, 0xffffffff);
            mChecked = typedArray.getBoolean(R.styleable.ToggleView_checked, false);
            mBorderWidth = (int) typedArray.getDimension(R.styleable.ToggleView_borderWidth, 3);
            typedArray.recycle();
        }
        defaultPadding = DisplayUtils.dp2px(getContext(), 5);
        mDefaultWidth = DisplayUtils.dp2px(getContext(), 50);
        mDefaultHeight = DisplayUtils.dp2px(getContext(), 30);
        animator = ValueAnimator.ofFloat(0, 1);
        startAnimation();

    }

    //
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//        marginLeft = Math.max(0, layoutParams.leftMargin);
//        marginTop = Math.max(0, layoutParams.topMargin);
//        marginRight = Math.max(0, layoutParams.rightMargin);
//        marginBottom = Math.max(0, layoutParams.bottomMargin);
//
//        Log.i(TAG, "marginLeft:\t" + marginLeft + ",\tmarginRight:\t" + marginRight + ",\tmarginTop:\t" + marginTop + "," +
//                "\tmarginBottom:\t" +
//                marginBottom);
//
//
//        int paddingLeft = getPaddingLeft();
//        int paddingRight = getPaddingRight();
//        int paddingTop = getPaddingTop();
//        int paddingBottom = getPaddingBottom();


//        int width = paddingLeft + paddingRight + marginLeft + marginRight + getMeasuredWidth();
//        int height = paddingTop + paddingBottom + marginTop + marginBottom + getMeasuredHeight();
//        setMeasuredDimension(measureWidth(widthMeasureSpec, width), measureHeight
//                (heightMeasureSpec, height));


    }


    private int measureWidth(int measureSpec, int viewGroupWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                /* 将剩余宽度和所有子View + padding的值进行比较，取小的作为ViewGroup的宽度 */
                result = Math.min(viewGroupWidth, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec, int viewGroupHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(viewGroupHeight, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
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
        mR = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2;
        Log.i(TAG, "left:\t" + left + ",\tright:\t" + right + ",\ttop:\t" + top + ",\tbottom:\t" +
                bottom);

        Log.i(TAG, "mR\t" + mR);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        int left1 = getLeft();
        int top1 = getTop();
        int right1 = getRight();
        int bottom1 = getBottom();
        Log.d(TAG, "left:\t" + left1 + ",\tright:\t" + right1 + ",\ttop:\t" + top1 + "," +
                "\tbottom:\t" +
                bottom1);
        Log.w(TAG, "left:\t" + mLeft + ",\tright:\t" + mRight + ",\ttop:\t" + mTop + "," +
                "\tbottom:\t" +
                mBottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBackgroundColor = mChecked ? mCheckedColor : mUnCheckedColor;
        mmBorderColor = mChecked ? mCheckedColor : mBorderColor;


        mTogglePaint.setColor(mToggleColor);


        RectF round = new RectF(mLeft + paddingLeft, mTop + paddingTop, mRight - paddingRight,
                mBottom - paddingBottom);
        RectF roundBorder = new RectF(mLeft + paddingLeft + mBorderWidth / 2, mTop + paddingTop +
                mBorderWidth/2, mRight - paddingRight -mBorderWidth/2, mBottom - paddingBottom - (mBorderWidth / 2));
        canvas.translate(-mLeft, -mTop);

        canvas.drawRoundRect(round, mR, mR, mBackgroundPaint);

        mBorderPaint.setColor(mmBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        canvas.drawRoundRect(roundBorder, mR, mR, mBorderPaint);


        mToggleBorderPaint.setStyle(Paint.Style.STROKE);
        mToggleBorderPaint.setStrokeWidth(mBorderWidth);
        mToggleBorderPaint.setColor(mmBorderColor);

        mBackgroundPaint.setColor(mBackgroundColor);
        int moveWith = getWidth() - paddingRight - paddingLeft - mR * 2;
//
        int cy = (mTop + mBottom + paddingTop - paddingBottom) / 2;
        int cxLeft = mLeft + paddingLeft + mR;
        int cxRight = mRight - paddingRight - mR;
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        if (mChecked) {
//            mBackgroundPaint.setColor(toggleChecked(fraction * moveWith));
            int evaluate = (int) argbEvaluator.evaluate(fraction, mUnCheckedColor, mCheckedColor);
            mBackgroundPaint.setColor(evaluate);
            canvas.drawRoundRect(round, mR, mR, mBackgroundPaint);
            canvas.drawRoundRect(roundBorder, mR, mR, mBorderPaint);
//            canvas.drawCircle(cxRight, cy, mR,
//                    mTogglePaint);
//            canvas.drawCircle(cxRight, cy, mR,
//                    mToggleBorderPaint);
            canvas.drawCircle(cxLeft + fraction * moveWith, cy, mR, mTogglePaint);
            canvas.drawCircle(cxLeft + fraction * moveWith, cy, mR-mBorderWidth/2, mToggleBorderPaint);

        } else {
//            mBackgroundPaint.setColor(toggleChecked(fraction * moveWith));
            int evaluate = (int) argbEvaluator.evaluate(fraction, mCheckedColor, mUnCheckedColor);
            mBackgroundPaint.setColor(evaluate);
            canvas.drawRoundRect(round, mR, mR, mBackgroundPaint);
            canvas.drawRoundRect(roundBorder, mR, mR, mBorderPaint);
//            canvas.drawCircle(cxLeft, cy, mR, mTogglePaint);
//            canvas.drawCircle(cxLeft, cy, mR, mToggleBorderPaint);
            canvas.drawCircle(cxRight - fraction * moveWith, cy, mR, mTogglePaint);
            canvas.drawCircle(cxRight - fraction * moveWith, cy, mR-mBorderWidth/2, mToggleBorderPaint);
        }
        canvas.translate(mLeft, mTop);

    }

    private void startAnimation() {

        animator.setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                Log.d(TAG, "fraction=" + fraction);
                invalidate();
            }
        });
        animator.start();
        if (fraction >= 1) {
            cancelAnimation();
        }
    }

    private void cancelAnimation() {
        if (animator.isRunning()) {
            animator.cancel();
        }
    }

    public interface OnToggleClickListener {
        void onToggleClick(boolean toggleState);
    }

    private OnToggleClickListener listener;

    public void setOnToggleClickListener(OnToggleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                mChecked = !mChecked;
                Log.i("ToggleView", "isOn  " + mChecked);
                requestLayout();//调用onLayout
                if (listener != null) {
                    listener.onToggleClick(mChecked);
                }
                startAnimation();
                break;
        }
        invalidate();
        Log.i("ToggleView", "super---" + super.onTouchEvent(event));
        return true;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public int getToggleBackgroundColor() {
        return mBackgroundColor;
    }

    public void setToggleBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    public int getToggleColor() {
        return mToggleColor;
    }

    public void setToggleColor(int mToggleColor) {
        this.mToggleColor = mToggleColor;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getCheckedColor() {
        return mCheckedColor;
    }

    public void setCheckedColor(int mCheckedColor) {
        this.mCheckedColor = mCheckedColor;
    }

    public int getUnCheckedColor() {
        return mUnCheckedColor;
    }

    public void setUnCheckedColor(int mUnCheckedColor) {
        this.mUnCheckedColor = mUnCheckedColor;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }


}
