package com.example.kevinjing.plan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Kevin on 2018/10/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private float rawX;
    private float rawY;
    private boolean canTouch = true;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        Log.w("DispatchEventActivity", "textView----super==============" + "super");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("DispatchEventActivity", "textView----dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }
}
