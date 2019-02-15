package com.example.kevinjing.plan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinjing.plan.custom.ProgressView;
import com.example.kevinjing.plan.kotlin.ToggleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/11/12<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class CustomViewActivity extends AppCompatActivity {
    private static final String TAG = "CustomViewActivity.class";
    @BindView(R.id.tv_toggle)
    ToggleView tvToggle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_toggle1)
    com.example.kevinjing.plan.custom.ToggleView tvToggle1;
    @BindView(R.id.tv_toggle2)
    ToggleView tvToggle2;
    @BindView(R.id.tv_toggle3)
    com.example.kevinjing.plan.custom.ToggleView tvToggle3;
    @BindView(R.id.tv_toggle4)
    com.example.kevinjing.plan.custom.ToggleView tvToggle4;
    @BindView(R.id.pv_progressView1)
    ProgressView pvProgressView1;
    @BindView(R.id.pv_progressView2)
    ProgressView pvProgressView2;
    @BindView(R.id.pv_progressView3)
    ProgressView pvProgressView3;
    @BindView(R.id.pv_progressView4)
    ProgressView pvProgressView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        tvToggle.setOnToggleClickListener(new ToggleView.OnToggleClickListener() {
            @Override
            public void onToggleClick(boolean toggleState) {
                Log.i("CustomViewActivity", "toggleState:" + (toggleState == true ? "开" : "关"));
                Toast.makeText(CustomViewActivity.this, toggleState == true ? "Java\t开" : "Java\t关", Toast
                        .LENGTH_SHORT).show();
            }
        });
        tvToggle.setCheckedColor(Color.parseColor("#669B7C"));
        pvProgressView3.setTextSize(20);
        pvProgressView3.setmProgress(50);

    }
}
