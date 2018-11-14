package com.example.kevinjing.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinjing.plan.kotlin.KotlinActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
                startActivity(new Intent(this,KotlinActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        btnEventBus.setText(messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
