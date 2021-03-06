package com.example.kevinjing.plan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinjing.plan.frame.ActivityManagerActivity;
import com.example.kevinjing.plan.frame.WindowManagerActivity;
import com.example.kevinjing.plan.kotlin.KotlinActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.tv_text)
    TextView tvText;
    private static final int CODE = 10;
    @BindView(R.id.btn_animation)
    Button btnAnimation;
    @BindView(R.id.btn_constraint)
    Button btnConstraint;
    @BindView(R.id.btn_constraint_set)
    Button btnConstraintSet;
    @BindView(R.id.btn_event_bus)
    Button btnEventBus;
    @BindView(R.id.btn_pm)
    Button btnPm;
    @BindView(R.id.btn_dd)
    Button btnDd;
    @BindView(R.id.btn_dispatch_event)
    Button btnDispatchEvent;
    @BindView(R.id.btn_sys_info)
    Button btnSysInfo;
    @BindView(R.id.btn_custom_view)
    Button btnCustomView;
    @BindView(R.id.btn_kotlin)
    Button btnKotlin;
    @BindView(R.id.btn_windowManager)
    Button btnWindowManager;
    @BindView(R.id.btn_activityManager)
    Button btnActivityManager;
    @BindView(R.id.btn_install_apk)
    Button btnInstallApk;
    @BindView(R.id.btn_constraint_custom_test)
    Button btnConstraintCustomTest;
    @BindView(R.id.btn_bezier_test)
    Button btnBezierTest;
    @BindView(R.id.btn_ruler_test)
    Button btnRulerTest;
    @BindView(R.id.btn_cycle)
    Button btnCycle;
    @BindView(R.id.btn_word)
    Button btnWord;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE:
                    Toast.makeText(MainActivity.this, "调用Handler", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnChange.setOnClickListener(this);
        btnAnimation.setOnClickListener(this);
        btnConstraint.setOnClickListener(this);
        btnConstraintSet.setOnClickListener(this);
        btnEventBus.setOnClickListener(this);
        btnPm.setOnClickListener(this);
        btnDd.setOnClickListener(this);
        btnDispatchEvent.setOnClickListener(this);
        btnSysInfo.setOnClickListener(this);
        EventBus.getDefault().register(this);

        btnCustomView.setOnClickListener(this);
        btnKotlin.setOnClickListener(this);
        btnWindowManager.setOnClickListener(this);
        btnActivityManager.setOnClickListener(this);
        btnInstallApk.setOnClickListener(this);
        btnConstraintCustomTest.setOnClickListener(this);
        btnBezierTest.setOnClickListener(this);
        btnRulerTest.setOnClickListener(this);
        btnCycle.setOnClickListener(this);
        btnWord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                tvText.setText("He");
                Message message = new Message();
                message.what = CODE;
                handler.sendMessage(message);
                break;
            case R.id.btn_animation:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.btn_constraint:
                startActivity(new Intent(this, ConstrainLayoutActivity.class));
                break;
            case R.id.btn_constraint_set:
                startActivity(new Intent(this, ConstraintSetActivity.class));
                break;
            case R.id.btn_event_bus:
                startActivity(new Intent(this, EventBusActivity.class));
                break;
            case R.id.btn_pm:
                startActivity(new Intent(this, PermissionManagerActivity.class));
                break;
            case R.id.btn_dd:
                startActivity(new Intent(this, DDActivity.class));
                break;
            case R.id.btn_dispatch_event:
                startActivity(new Intent(this, DispatchEventActivity.class));
                break;
            case R.id.btn_sys_info:
                startActivity(new Intent(this, SystemInfoActivity.class));
                break;
            case R.id.btn_custom_view:
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.btn_kotlin:
                startActivity(new Intent(this, KotlinActivity.class));
                break;
            case R.id.btn_windowManager:
                startActivity(new Intent(this, WindowManagerActivity.class));
                break;
            case R.id.btn_activityManager:
                startActivity(new Intent(this, ActivityManagerActivity.class));
                break;
            case R.id.btn_install_apk:
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                    installNApk();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    installOApk();
                }
                break;
            case R.id.btn_constraint_custom_test:
                startActivity(new Intent(this, ConstrainCustomViewTestActivity.class));
                break;
            case R.id.btn_bezier_test:
                startActivity(new Intent(this, BezierTestActivity.class));
                break;
            case R.id.btn_ruler_test:
                startActivity(new Intent(this, RulerViewActivity.class));
                break;
            case R.id.btn_cycle:
                startActivity(new Intent(this, CycleActivity.class));
                break;
            case R.id.btn_word:
                startActivity(new Intent(this, WordActivity.class));
                break;
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void installOApk() {
        boolean b = getPackageManager().canRequestPackageInstalls();
        if (b) {
            installAPP();
//            有权限了
        } else {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivityForResult(intent, 102);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 102);
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    private void installNApk() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivityForResult(intent, 101);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 100);
//            }
        } else {
            installAPP();
        }
    }

    private void installAPP() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + "Damt.apk";
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.example.kevinjing.plan.fileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
            Toast.makeText(this, "路径不存在", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installNApk();
                }
            case 102:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installAPP();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main", "onActivityResult执行了");
//        switch (requestCode) {
//            case 101:
////                int i = ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_INSTALL_PACKAGES);
////                Log.d("Main", i + "");
////                if (i != PackageManager.PERMISSION_GRANTED) {
////
////                } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    installAPP();
//                }
////                }
//                break;
//            default:
//
//                break;
//        }
        if (requestCode == 102) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean b = getPackageManager().canRequestPackageInstalls();
                if (b) {
                    Toast.makeText(this, "可以了", Toast.LENGTH_SHORT).show();
                    installAPP();
                } else {
                    Toast.makeText(this, "不可以", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main", "onResume执行了");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main", "onPause执行了");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        btnEventBus.setText(messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Main", "onDestroy执行了");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
