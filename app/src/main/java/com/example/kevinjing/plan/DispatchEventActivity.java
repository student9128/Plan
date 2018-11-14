package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class DispatchEventActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.sv_scroll_view)
    ScrollView svScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_event);
        ButterKnife.bind(this);
        svScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("DispatchEventActivity", "scrollView按下事件");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("DispatchEventActivity", "scrollView移动事件");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("DispatchEventActivity", "scrollView抬起事件");
                        break;
                }
                return true;
            }
        });
        svScrollView.setOnClickListener(this);
//        tvText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d("DispatchEventActivity", "textView按下事件");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.d("DispatchEventActivity", "textView移动事件");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.d("DispatchEventActivity", "textView抬起事件");
//                        break;
//                }
//                return true;
//            }
//        });
        tvText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sv_scroll_view:
                Log.d("DispatchEventActivity", "scrollView点击事件");
                break;
            case R.id.tv_text:
                Log.d("DispatchEventActivity", "textView点击事件");
                break;
        }
    }
}
