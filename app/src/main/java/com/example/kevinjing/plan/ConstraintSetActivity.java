package com.example.kevinjing.plan;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class ConstraintSetActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.constraint_container)
    ConstraintLayout constraintContainer;

    private ConstraintSet firstC, secondC;

    private boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintset);
        ButterKnife.bind(this);
        btnChange.setOnClickListener(this);
        firstC = new ConstraintSet();
        secondC = new ConstraintSet();
        firstC.clone(constraintContainer);
        secondC.clone(this, R.layout.activity_constraintset_2);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                if (flag) {
                    flag = false;
                    TransitionManager.beginDelayedTransition(constraintContainer);
                    firstC.applyTo(constraintContainer);
                } else {
                    flag = true;
                    TransitionManager.beginDelayedTransition(constraintContainer);
                    secondC.applyTo(constraintContainer);

                }
                break;
        }
    }
}
