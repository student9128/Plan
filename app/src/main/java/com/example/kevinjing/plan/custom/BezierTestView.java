package com.example.kevinjing.plan.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kevin on 2018/11/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class BezierTestView extends View {
    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;
    private Paint paint;

    private float mAuxiliaryX;
    private float mAuxiliaryY;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private Path mPath = new Path();

    public BezierTestView(Context context) {
        this(context,null);
    }

    public BezierTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(20);
        mPaintAuxiliary.setStrokeCap(Paint.Cap.ROUND);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(20);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2 - 200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
//        mPath.moveTo(mStartPointX, mStartPointY);
        Path path = new Path();
        Path path1 = new Path();
        path1.moveTo(0, 400);
        path.moveTo(0, 400);
        canvas.drawPoint(200,400,mPaintAuxiliary);
        canvas.drawPoint(400,400,mPaintAuxiliary);
        canvas.drawPoint(600,400,mPaintAuxiliary);
        canvas.drawPoint(800,400,mPaintAuxiliary);
        path.quadTo(100,300,200,400);
        path.quadTo(300,500,400,400);
        path1.quadTo(100, 500, 200, 400);
        path1.quadTo(300,300,400,400);

        canvas.drawPath(path,mPaintBezier);
        canvas.drawPath(path1,paint);
        // 辅助点
//        canvas.drawPoint(mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
//        canvas.drawText("控制点", mAuxiliaryX, mAuxiliaryY, mPaintAuxiliaryText);
//        canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
//        canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
        // 辅助线
//        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
//        canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        // 二阶贝塞尔曲线
//        mPath.quadTo(mAuxiliaryX, mAuxiliaryY, mEndPointX, mEndPointY);
//        canvas.drawPath(mPath, mPaintBezier);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                mAuxiliaryX = event.getX();
//                mAuxiliaryY = event.getY();
//                invalidate();
//        }
//        return true;
//    }

}
