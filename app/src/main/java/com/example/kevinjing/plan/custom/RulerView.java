package com.example.kevinjing.plan.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.util.DisplayUtils;

/**
 * Created by Kevin on 2018/11/29<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class RulerView extends View {
    private Paint mPaint, mTextPaint;
    private int mWidth, mHeight;
    private int mDefaultWidth, mDefaultHeight;
    /**
     * 刻度尺颜色
     */
    private int backgroundColor;
    /**
     * 刻度尺刻度颜色
     */
    private int markColor;
    /**
     * 刻度尺长刻度颜色
     */
    private int superMarkColor;
    /**
     * 刻度尺字体颜色
     */
    private int textColor;
    /**
     * 刻度尺字体大小
     */
    private int textSize;

    private int minNumber, maxNumber, currentNumber, numberUnit;
    private int minValue, maxValue, currentValue;
    private float markUnit;
    private int markInterval;
    private int numberRangeDistance;
    /**
     * 当滑动距离大于这个值，控件才可以滑动
     */
    private int touchSlop;
    private int minimumFlingVelocity;
    private int maximumFlingVelocity;

    private Scroller mScroller;

    private VelocityTracker velocityTracker;
    private boolean isMove;
    private float downX;
    private float lastX;
    private float lastY;
    private float dx;
    private float currentDistance;
    private int halfWidth;
    private int widthRangeNumber;
    private int width;
    private int height;
    private static final String TAG = "RulerView.class";
    private int numberPerCount;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RulerView);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.RulerView_rulerBackgroundColor, 0xfff0f3f3);
            markColor = typedArray.getColor(R.styleable.RulerView_rulerMarkColor, 0xff2f89fc);
            superMarkColor = typedArray.getColor(R.styleable.RulerView_rulerSuperMarkColor, 0xff2f89fc);
            textColor = typedArray.getColor(R.styleable.RulerView_rulerTextColor, 0xff2f89fc);
            textSize = typedArray.getDimensionPixelSize(R.styleable.RulerView_rulerTextSize, DisplayUtils.sp2px(context, 14));
        }

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();
        minimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        maximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);

        mDefaultHeight = DisplayUtils.dp2px(context, 50);
        minValue = 0;
        maxValue = 100;
        markUnit = .1f;//最小单位是0.1
        markInterval = DisplayUtils.dp2px(context, 10);//可读性间距是10dp

        minNumber = minValue * 10;
        maxNumber = maxValue * 10;
        numberUnit = (int) (markUnit * 10);
        currentNumber = currentValue * 10;
        numberRangeDistance = (maxNumber - minNumber) / numberUnit * markInterval;
        numberPerCount = 10;
        currentDistance = (currentNumber - minNumber) / numberUnit * markInterval;
        mScroller = new Scroller(context);
        if (width != 0) {
            widthRangeNumber = width / markInterval * numberUnit;//可以变化范围就是宽度/间距
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureSize(true, widthMeasureSpec);
        height = measureSize(false, heightMeasureSpec);
        halfWidth = width / 2;
        if (widthRangeNumber == 0) {
            widthRangeNumber = (width / markInterval * numberUnit);
        }
        setMeasuredDimension(width, height);
    }

    private int measureSize(boolean isWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = specSize;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = Math.min(result, specSize);
                break;
            case MeasureSpec.AT_MOST:
                if (!isWidth) {
                    result = mDefaultHeight;
                    result = Math.min(result, specSize);
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);
        drawMark(canvas);

        drawIndicator(canvas);


    }

    private void drawIndicator(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#dd7c1b"));
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(halfWidth, 0, halfWidth, 25, mPaint);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
    }

    private void drawMark(Canvas canvas) {
        mPaint.setColor(markColor);
        mPaint.setStrokeWidth(6);
        canvas.drawLine(0, 3, width, 0, mPaint);//上面的线
        int offset = currentValue - minValue;

//        int startNum = halfWidth - offset + left / markInterval * numberUnit;
        //currentDistance就是当前数据-halfWidth表名这个数据在是屏幕正中间，起始值在边上
        //不减-halfWidth，startNum从零开始，居中，不会往左去
        int startNum = (int) ((currentDistance - halfWidth) / markInterval * numberUnit + minNumber);


//        int startNum = (int) ((currentDistance) / markInterval * numberUnit + minNumber);//从最左边开始绘
        Log.w(TAG, String.format("currentNum=%s,currentDistance=%s,currentDistance/MarkInterval=%s,markInterval=%s",
                currentNumber, currentDistance, currentDistance / markInterval,markInterval));

        Log.i(TAG, String.format("startNum=%s", startNum));
        int expandUnit = numberUnit * 2;
        startNum -= expandUnit;
        if (startNum < minNumber) {
            startNum = minNumber;
        }
        int rightMaxNum = (startNum+expandUnit) + widthRangeNumber+expandUnit;
//        int rightMaxNum = startNum+widthRangeNumber;
        Log.w(TAG, "rightMaxNum===" + rightMaxNum);
        Log.w(TAG, "widthRangeNumber===" + widthRangeNumber);
        if (rightMaxNum > maxNumber) {
            rightMaxNum = maxNumber;
        }
        float distance = halfWidth - (currentDistance - (startNum - minNumber) / numberUnit * markInterval);
//        int distance = (int) (currentDistance-(startNum-minNumber)/numberUnit*markInterval);

        Log.d(TAG, "distance---" + distance);
        Log.d(TAG, String.format("halfWidth=%s,currentDistance=%s,startNum=%s,minNUmber=%s,numberUnit=%s,markInterval=%s",
                halfWidth, currentDistance, startNum, minNumber, numberUnit, markInterval));
        int perUnitCount = numberUnit * numberPerCount;
        while (startNum <= rightMaxNum) {
            Log.w(TAG, "startNum===" + startNum);
            if (startNum % perUnitCount == 0) {
                mPaint.setStrokeWidth(10);
                canvas.drawLine(distance, 0, distance, 20, mPaint);
                float fNum = startNum / 10f;
                String text = Float.toString(fNum);
                Log.w(TAG, "text===" + text);
                if (text.endsWith(".0")) {
                    text = text.substring(0, text.length() - 2);
                }
                float textWidth = mTextPaint.measureText(text);
                canvas.drawText(text, distance - textWidth / 2, 25 + textSize, mTextPaint);
            } else {
                mPaint.setStrokeWidth(6);
                canvas.drawLine(distance, 0, distance, 16, mPaint);
            }
            startNum += numberUnit;
            distance += markInterval;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);//强制停止滑动
                isMove = false;
                downX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = x - lastX;
                Log.i(TAG, String.format("dx=%s", dx));
                if (!isMove) {
                    float dy = y - lastY;
                    if (Math.abs(dx) < Math.abs(dy) || Math.abs(x - downX) < touchSlop) {
                        break;
                    }
                    isMove = true;
                }
                //向左滑动，currentDistance变大，向右滑动，currentDistance变小，所以取负值
                currentDistance += -dx;
                currentDistance = Math.min(Math.max(currentDistance, 0), numberRangeDistance);
                Log.i(TAG, String.format("currentDistance==%s,numberRangeDistance=%d", currentDistance, numberRangeDistance));
                currentNumber = (int) (minNumber + (currentDistance / markInterval) * numberUnit);
                currentValue = currentNumber / 10;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity);
                float xVelocity = velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= minimumFlingVelocity) {
                    mScroller.fling((int) currentDistance, 0, (int) -xVelocity, 0, 0, numberRangeDistance, 0, 0);
                    invalidate();
                } else {
                    currentNumber = minNumber + Math.round(currentDistance / markInterval) * numberUnit;
                    currentNumber = Math.min(Math.max(currentNumber, minNumber), maxNumber);
                    currentDistance = (currentNumber - minNumber) / numberUnit * markInterval;
                    currentValue = currentNumber / 10;
                    invalidate();
                }
                break;
        }
        Log.i(TAG, "currentDistance---" + currentDistance);
        lastX = x;
        lastY = y;
        invalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//还在滑动
            if (mScroller.getCurrX() != mScroller.getFinalX()) {
                currentDistance = mScroller.getCurrX();
                currentDistance = Math.min(Math.max(currentDistance, 0), numberRangeDistance);
                currentNumber = (int) (minNumber + (currentDistance / markInterval) * numberUnit);
                invalidate();
            } else {
                currentNumber = minNumber + Math.round(currentDistance / markInterval) * numberUnit;
                currentNumber = Math.min(Math.max(currentNumber, minNumber), maxNumber);
                currentDistance = (currentNumber - minNumber) / numberUnit * markInterval;
                currentValue = currentNumber / 10;
                invalidate();
            }
        }
    }
}
