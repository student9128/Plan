package com.example.kevinjing.plan;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

/**
 * Created by Kevin on 2018/10/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class CustomScrollView extends ScrollView {
    private boolean isMove = false;
    private float rawX;
    private float rawY;
    private boolean canIntercept = true;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("DispatchEventActivity", "scrollView----onTouchEvent");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                Log.i("DispatchEventActivity", "scrollView----onInterceptTouchEvent===down");
                rawX = ev.getRawX();
                rawY = ev.getRawY();
                canIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float moveY = ev.getRawY();
                float moveX = ev.getRawX();
                if (Math.abs(moveY - rawY) > 10 || Math.abs(moveX -
                        rawX) > 10) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    Log.i("DispatchEventActivity", "scrollView----onInterceptTouchEvent===move");
                    canIntercept = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(ev.getRawY() - rawY) > 0 || Math.abs(ev.getRawX() -
                        rawX) > 0) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    Log.i("DispatchEventActivity", "scrollView----onInterceptTouchEvent===move");
                    canIntercept = true;
                    return true;
                }
                Log.i("DispatchEventActivity", "scrollView----onInterceptTouchEvent===up");
                isMove = false;
                break;
        }
        Log.i("DispatchEventActivity", "scrollView----onInterceptTouchEvent=super==" + canIntercept);
//        return super.onInterceptTouchEvent(ev);
        return canIntercept;
    }

    private int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}
