package com.example.kevinjing.plan;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Barrier;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.constraint.Placeholder;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/16<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ConstrainLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_contract)
    TextView tvContract;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_contract)
    EditText etContract;
    @BindView(R.id.barrier)
    Barrier barrier;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.tv_circle)
    TextView tvCircle;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.pl_place_holder)
    Placeholder plPlaceHolder;
    @BindView(R.id.constraint_container)
    ConstraintLayout constraintContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout);
        ButterKnife.bind(this);
//        ConstraintLayout.LayoutParams clp = new ConstraintLayout.LayoutParams(ConstraintLayout
//                .LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
//        clp.circleAngle=45;
//        clp.circleRadius=100;
//        clp.circleConstraint = R.id.btn_constraint;
        btn1.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                TransitionManager.beginDelayedTransition(constraintContainer);
                plPlaceHolder.setContentId(R.id.iv);
                break;
        }
    }
}
