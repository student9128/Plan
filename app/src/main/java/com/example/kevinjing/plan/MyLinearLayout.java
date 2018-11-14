package com.example.kevinjing.plan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.LinearLayout;

/**
 * Created by Kevin on 2018/10/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class MyLinearLayout extends LinearLayout {

    private float rawX;
    private float rawY;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0://down
                rawX = ev.getRawX();
                rawY = ev.getRawY();
                break;
            case 1://up
                break;
            case 2://move
                float moveY = ev.getRawY();
                float moveX = ev.getRawX();
                if (Math.abs(moveY - rawY) > 10 || Math.abs(moveX -
                        rawX) > 10) {
//                    ViewParent parent = getParent();
//                    if (parent != null) {
//                        parent.requestDisallowInterceptTouchEvent(true);
//                    }
                    Log.i("DispatchEventActivity", "linearLayout----onInterceptTouchEvent===move");
                    return true;
                }
                rawX = ev.getRawX();
                rawY = ev.getRawY();

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
