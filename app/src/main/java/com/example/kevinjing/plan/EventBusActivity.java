package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class EventBusActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_message)
    Button btnMessage;
    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        btnMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_message) {
            EventBus.getDefault().post(new MessageEvent("Welcome back, you by event bus."));
            finish();
        }
    }
}
