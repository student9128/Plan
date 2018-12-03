package com.example.kevinjing.plan.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Kevin on 2018/11/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class BezierWaveTestView extends View implements View.OnClickListener {
    private Paint mPaint,mPaint2;
    private Path mPath;
    private static final String TAG = "BezierWaveView";
    private int mScreenHeight;
    private int mScreenWidth;
    private int mWaveCount;
    private int centerY;
    private int waveLength = 1000;
    private int offset;
    public BezierWaveTestView(Context context) {
        this(context,null);
    }

    public BezierWaveTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierWaveTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "w=" + w + ",\t h=" + h + ",\t oldw=" + oldw + ",\t oldh=" + oldh);
        mScreenWidth = w;
        mScreenHeight = h;
        mWaveCount = (int) Math.round(mScreenWidth / waveLength + 1.5);
        centerY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#FF8A98"));
        mPaint2.setColor(Color.parseColor("#7696DB"));
        mPath.reset();//一定要reset
        mPath.moveTo(-waveLength + offset, centerY);
        Path path = new Path();
        path.moveTo(-waveLength / 2 + offset, centerY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo((-waveLength * 3 / 4) + i * waveLength + offset, centerY + 100, (-waveLength / 2) + i * waveLength + offset, centerY);
            mPath.quadTo((-waveLength/4)+i*waveLength+offset,centerY-100,i*waveLength+offset,centerY);
            path.quadTo((-waveLength*3/4)+i*waveLength+offset,centerY-100,(-waveLength / 2) + i * waveLength + offset,centerY);
            path.quadTo((-waveLength  / 4) + i * waveLength + offset, centerY + 100, i * waveLength + offset, centerY);
        }
//        path.lineTo(mScreenWidth, mScreenHeight);
//        path.lineTo(0, mScreenHeight);
//        path.close();
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
//        canvas.drawPath(path,mPaint2);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofInt(0, waveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
