package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kevinjing.plan.custom.RulerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/11/30<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class RulerViewActivity extends AppCompatActivity {
    @BindView(R.id.rulerView)
    RulerView rulerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler_view);
        ButterKnife.bind(this);
    }
}
