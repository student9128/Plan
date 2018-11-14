package com.example.kevinjing.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Kevin on 2018/10/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class DDActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    ViewGroup vgRoot;
    private ScrollView scrollView;
    private TextView btn;
    private int sx;
    private int sy;
    private int screenHeight;
    private int screenWidth;
    private int width2;
    private int height2;
    private int anInt;
    private TextView tv, tv1, tv2,tv3,tv4,tv5;
    private int totalHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd);
        vgRoot = (ViewGroup) findViewById(R.id.root);
        scrollView = findViewById(R.id.sv_scroll_view);
        btn = findViewById(R.id.btn_2);
        btn.setOnClickListener(this);
        anInt = dp2px(this, 150);
        tv = findViewById(R.id.tv_text);
        tv1 = findViewById(R.id.tv_text1);
        tv2 = findViewById(R.id.tv_text2);
        tv3 = findViewById(R.id.tv_text3);
        tv4 = findViewById(R.id.tv_text4);
        tv5 = findViewById(R.id.tv_text5);
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);


        scrollView.setOnTouchListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
//        Log.i("DDActivity", "screenHeight:=>" + screenHeight);
//        Log.i("DDActivity", "screenWidth:=>" + screenWidth);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width2 = outMetrics.widthPixels;
        height2 = outMetrics.heightPixels;
//        Log.i("DDActivity", "height2:=>" + height2);
//        Log.i("DDActivity", "width2:=>" + width2);
        int l = scrollView.getLeft();
        int r = scrollView.getRight();
        int t = scrollView.getTop();
        int b = scrollView.getBottom();

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = scrollView.getHeight();
                totalHeight = scrollView.getChildAt(0).getHeight();
                int measuredHeight = scrollView.getMeasuredHeight();
//                Log.w("DDActivity", "childHeight:=---------->" + totalHeight);
//                Log.i("DDActivity", "scrollheight2:=>" + height);
//                Log.i("DDActivity", "scrollmeasuredHeight:=>" + measuredHeight);
                int vgHeight = vgRoot.getMeasuredHeight();
                int vgWidth = vgRoot.getMeasuredWidth();
//                Log.w("DDActivity", "vgHeight:=>" + vgHeight);
//                Log.w("DDActivity", "vgWidth:=>" + vgWidth);
                scrollView.layout(0, vgHeight - dp2px(DDActivity.this, 150), scrollView.getWidth(),
                        vgHeight -
                                anInt +
                                totalHeight);
                int l = scrollView.getLeft();
                int r = scrollView.getRight();
                int t = scrollView.getTop();
                int b = scrollView.getBottom();
                // 更改imageView在窗体的位置
//                Log.e("DDActivity", "l:=>" + l);
//                Log.e("DDActivity", "r:=>" + r);
//                Log.e("DDActivity", "t:=>" + t);
//                Log.e("DDActivity", "b:=>" + b);

            }
        });

//        scrollView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                scrollView.getViewTreeObserver().removeOnPreDrawListener(this);
//                return true;
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_2) {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.tv_text) {

            Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.tv_text1) {

            Toast.makeText(this, "Text1", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.tv_text2) {

            Toast.makeText(this, "Text2", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConstraintSetActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int vgHeight = vgRoot.getMeasuredHeight();
        int vgWidth = vgRoot.getMeasuredWidth();
//        Log.w("DDActivity", "vgHeight:=>" + vgHeight);
//        Log.w("DDActivity", "vgWidth:=>" + vgWidth);
        switch (v.getId()) {
            case R.id.sv_scroll_view:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 获取手指第一次接触屏幕
                        sx = (int) event.getRawX();
                        sy = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动对应的事件
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        // 获取手指移动的距离
                        int dx = x - sx;
                        int dy = y - sy;
                        int l = scrollView.getLeft();
                        int r = scrollView.getRight();
                        int t = scrollView.getTop();
                        int b = scrollView.getBottom();
//                        Log.i("DDActivity", "l:=>" + l);
//                        Log.i("DDActivity", "r:=>" + r);
//                        Log.i("DDActivity", "t:=>" + t);
//                        Log.i("DDActivity", "b:=>" + b);
//                        scrollView.layout(l, t + dy, r, b + dy);
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vgRoot.getLayoutParams();

                        Log.d("DDActivity", "dy==>" + dy);
                        anInt = dp2px(this, 150);
//                        Log.w("DDActivity", "anInt::::::" + anInt);
                        if (t >= vgHeight - anInt) {
                            t=vgHeight-anInt;
                            scrollView.layout(l, t, r, t+ totalHeight);
                        }
                        if (b <= vgHeight) {
//                            b= vgHeight;
                            scrollView.layout(l, vgHeight - totalHeight, r, vgHeight);
                        }
                        if ((vgHeight - t) > anInt) {
                            if (b > vgHeight) {
//                                Log.i("DDActivity", "dy:=1=------=>" + dy);
                                if (dy > 0 && dy <= (vgHeight - t - anInt)) {
//                                    if (dy >= 100) {
//                                        dy = 100;
//                                    }
                                    scrollView.layout(l, t + dy, r, b + dy);
                                }
                                if (dy < 0 && Math.abs(dy) <= (b - vgHeight)) {
                                    if (dy <= -100) {
                                        dy = -100;
                                    }
                                    scrollView.layout(l, t + dy, r, b + dy);

                                }
                            } else if (b < vgHeight) {
                                scrollView.layout(l, vgHeight - totalHeight, r, vgHeight);
                            } else if (b == vgHeight) {
                                if (dy < 0) {
                                    if (dy <= -100) {
                                        dy = -100;
                                    }
                                    scrollView.layout(l, t, r, b);
                                } else {
                                    if (dy >= 100) {
                                        dy = 100;
                                    }
                                    scrollView.layout(l, t + dy, r, b + dy);
                                }
                            }
                        } else if ((vgHeight - t) == anInt) {
                            if (dy > 0) {
                                scrollView.setTranslationY(0);
                                scrollView.layout(l, t, r, b);
                            } else {
                                if (b > vgHeight) {
                                    if (dy < -100) {
                                        dy = -100;
                                    }
                                    scrollView.layout(l, t + dy, r, b + dy);
                                }
                            }
                        } else {
                            if (t > vgHeight - anInt) {
                                scrollView.layout(l, vgHeight - anInt, r, vgHeight - anInt + totalHeight);
                            }
                            if (dy > 0) {
                                scrollView.layout(l, vgHeight - anInt, r, vgHeight - anInt + totalHeight);
                            } else {
                                scrollView.layout(l, t + dy, r, b + dy);
                            }
                        }
                        // 获取移动后的位置
                        sx = (int) event.getRawX();
                        sy = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
                        int lastT = scrollView.getTop();
                        int lastB = scrollView.getBottom();
//                        Log.i("DDActivity", "lastT:=---->" + lastT);
//                        Log.i("DDActivity", "lastB:=---->" + lastB);
                        if (lastB < vgHeight) {
                            scrollView.layout(scrollView.getLeft(), vgHeight - totalHeight,
                                    scrollView.getRight(),
                                    vgHeight);
                        }
                        if (lastT > vgHeight - anInt) {
                            scrollView.layout(scrollView.getLeft(), vgHeight - anInt, scrollView
                                    .getRight(), vgHeight - anInt + totalHeight);
                        }
                        break;
                }
                break;
        }
//        v.postInvalidate();
//        return super.onTouchEvent(event);// 不会中断触摸事件的返回
        return true;
    }

    private int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    private int sp2px(Context context, int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

}
