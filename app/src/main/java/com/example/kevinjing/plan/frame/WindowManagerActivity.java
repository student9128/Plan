package com.example.kevinjing.plan.frame;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinjing.plan.R;
import com.example.kevinjing.plan.custom.TestView;
import com.example.kevinjing.plan.custom.ToggleView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/11/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class WindowManagerActivity extends AppCompatActivity implements View.OnClickListener {


    //    @BindView(R.id.tv_text)
//    TextView tvText;
    private Button button;
    private WindowManager.LayoutParams layoutParams;
    private ToggleView toggleView;
    private WindowManager windowManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_window_manager);
//        ButterKnife.bind(this);
//        tvText.setOnClickListener(this);
////        button = new Button(this);
////        button.setText("Hello");
        toggleView = new ToggleView(this);
        windowManager = getWindowManager();
//        WindowManager windowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
//        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
//        layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        layoutParams.width = 1000;
//        layoutParams.height = 1000;
//
////        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
////            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
////        } else {
////            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
////        }
//        layoutParams.format = PixelFormat.TRANSPARENT;
//        layoutParams.gravity = Gravity.BOTTOM;
//        IBinder windowToken = getWindow().getDecorView().getWindowToken();
//        layoutParams.token = windowToken;
        layoutParams = new WindowManager.LayoutParams();
//悬浮窗参数设置
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//悬浮窗宽度
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//悬浮窗高度
        layoutParams.x = 500;//悬浮窗位置
        layoutParams.y = 500;//悬浮窗位置

//重点，类型设置为dialog类型,可无视权限!
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//重点,必须设置此参数，用于窗口机制验证
//        IBinder windowToken = getWindow().getDecorView().getWindowToken();
//        layoutParams.token = windowToken;


//
//
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please granted systemm alter window permission", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
            } else {
                Class clazz = Settings.class;
                Field field = null;
                try {
                    field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
                    Intent intent = new Intent(field.get(null).toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        } else {
//            WindowManager windowManager = getWindowManager();
        }
            windowManager.addView(toggleView, layoutParams);
//
//
//        Button bb=new Button(getApplicationContext());
//        WindowManager wm=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//
//        /**
//         *以下都是WindowManager.LayoutParams的相关属性
//         * 具体用途请参考SDK文档
//         */
//        wmParams.type=2003;   //这里是关键，你也可以试试2003
//        wmParams.format=1;
//        /**
//         *这里的flags也很关键
//         *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
//         *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
//         */
//        wmParams.flags=40;
//        wmParams.width=4000;
//        wmParams.height=4000;
//        wm.addView(bb, wmParams); //创建View

        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.BLACK);
        textView.setText("LLLLL");
        textView.setTextSize(10);
        textView.setTextColor(Color.RED);

//        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
//
//        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = 300;
//        //params.gravity=Gravity.BOTTOM;
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplication(), "不需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        WindowManager windowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
////        WindowManager windowManager = getWindowManager();
//        windowManager.addView(textView, params);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                windowManager.addView(button, layoutParams);
            }
        } else {
            Toast.makeText(this, "Please granted system alter window permission", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text:
                Toast.makeText(this, "Please granted systemm alter window permission", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        windowManager.removeView(toggleView);

//        windowManager.removeViewImmediate(toggleView);
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        windowManager.removeView(toggleView);
//    }
}
