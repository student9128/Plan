package com.example.kevinjing.plan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by Kevin on 2018/10/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class MoveView extends AppCompatTextView {
    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
