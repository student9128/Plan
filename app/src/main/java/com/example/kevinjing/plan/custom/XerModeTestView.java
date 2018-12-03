package com.example.kevinjing.plan.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kevin on 2018/11/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class XerModeTestView extends View {
    private Paint mPaint1, mPaint2;
    private Bitmap dstBitmap, srcBitmap;

    public XerModeTestView(Context context) {
        this(context, null);
    }

    public XerModeTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XerModeTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

        dstBitmap = makeBitmap(400, 400, 0);
        srcBitmap = makeBitmap(400, 400, 1);
    }

    private Bitmap makeBitmap(int w, int h, int type) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        switch (type) {
            case 0:
                p.setColor(Color.RED);
                canvas.drawOval(new RectF(0, 0, w, h), p);
                break;
            case 1:
                p.setColor(Color.GREEN);
                canvas.drawRect(new RectF(0, 0, w, h), p);
                break;
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = canvas.saveLayer(null,null, Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(dstBitmap, 0, 0, mPaint1);//圆形
        mPaint1.setColor(Color.RED);
        Path path = new Path();
        path.lineTo(400, 0);
        path.rLineTo(0, 400);
        path.rLineTo(-400, 0);
        path.close();
        canvas.drawPath(path,mPaint1);

        mPaint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, 200, 200, mPaint1);//方形
        mPaint1.setXfermode(null);
        canvas.restoreToCount(i);
    }
}
