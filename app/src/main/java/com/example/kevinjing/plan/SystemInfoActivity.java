package com.example.kevinjing.plan;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/22<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class SystemInfoActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_get_info)
    Button btnGetInfo;
    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info);
        ButterKnife.bind(this);
        btnGetInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_info) {
            tvText.setText("手机型号：" + Build.MODEL + "\n" + "SDK版本：" + Build.VERSION
                    .SDK_INT + "\n" + "系统版本:" + Build.VERSION.RELEASE + "\n" + "软件版本：" +
                    getAppVersionName(this) + "\n" + "系统制造商：" + Build.BRAND + "\n" + "编译时间：" + Build
                    .TIME + "\n"
                    + "设备参数：" + Build.DEVICE + "\n" + "主板：" + Build.BOARD + "\n" + "boos版本：" + Build.BOOTLOADER);
            Log.d("SystemInfoActivity", "os.name is " + System.getProperty("os.name"));
            Log.d("SystemInfoActivity", "os.arch is " + System.getProperty("os.arch"));
            Log.d("SystemInfoActivity", "user.home is " + System.getProperty("user.home"));
            Log.d("SystemInfoActivity", "user.name is " + System.getProperty("user.name"));
            Log.d("SystemInfoActivity", "user.dir is " + System.getProperty("user.dir"));
            Log.d("SystemInfoActivity", "java.class.path is " + System.getProperty("java.class" +
                    ".path"));
            Log.d("SystemInfoActivity", "java.class.version is " + System.getProperty("java.class" +
                    ".version"));
            Log.d("SystemInfoActivity", "java.version is " + System.getProperty("java.version"));
            Log.d("SystemInfoActivity", "java.home is " + System.getProperty("java.home"));
        }
    }

    private String getAppVersionName(Context context) {
        String versionName = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.example.kevinjing.plan", 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
