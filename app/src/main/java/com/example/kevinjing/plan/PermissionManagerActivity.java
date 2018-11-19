package com.example.kevinjing.plan;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class PermissionManagerActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_request_pm)
    Button btnRequestPm;

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    static final String[] p = new String[]{
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };
    @BindView(R.id.btn_request_install)
    Button btnRequestInstall;
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm);
        ButterKnife.bind(this);
        btnRequestPm.setOnClickListener(this);
        btnRequestInstall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_request_pm) {
            mPermissionsChecker = new PermissionsChecker(this);
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
            }
        }
        if (v.getId() == R.id.btn_request_install) {
            mPermissionsChecker = new PermissionsChecker(this);
            if(mPermissionsChecker.lacksPermissions(p)){
                startPermissionsActivity();
            }
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, p);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
