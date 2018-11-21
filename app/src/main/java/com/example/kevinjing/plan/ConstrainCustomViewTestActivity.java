package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kevin on 2018/11/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 * 测试自定义view 在ScrollView嵌套ConstraintLayout中是否可以正常显示
 */
public class ConstrainCustomViewTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrain_custom_view_test);
    }
}
