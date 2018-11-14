package com.example.kevinjing.plan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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
    }
}
