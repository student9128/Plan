package com.example.kevinjing.plan;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.xml.validation.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2018/10/15<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_rotate_1)
    Button btnRotate1;
    @BindView(R.id.btn_scale_1)
    Button btnScale1;
    @BindView(R.id.btn_translate_1)
    Button btnTranslate1;
    @BindView(R.id.btn_alpha_1)
    Button btnAlpha1;
    @BindView(R.id.btn_rotate_2)
    Button btnRotate2;
    @BindView(R.id.btn_scale_2)
    Button btnScale2;
    @BindView(R.id.btn_translate_2)
    Button btnTranslate2;
    @BindView(R.id.btn_alpha_2)
    Button btnAlpha2;
    @BindView(R.id.btn_rotate_3)
    Button btnRotate3;
    @BindView(R.id.btn_scale_3)
    Button btnScale3;
    @BindView(R.id.btn_translate_3)
    Button btnTranslate3;
    @BindView(R.id.btn_alpha_3)
    Button btnAlpha3;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        ivImage.setOnClickListener(this);
        btnRotate1.setOnClickListener(this);
        btnScale1.setOnClickListener(this);
        btnTranslate1.setOnClickListener(this);
        btnAlpha1.setOnClickListener(this);
        btnRotate2.setOnClickListener(this);
        btnScale2.setOnClickListener(this);
        btnTranslate2.setOnClickListener(this);
        btnAlpha2.setOnClickListener(this);
        btnRotate3.setOnClickListener(this);
        btnScale3.setOnClickListener(this);
        btnTranslate3.setOnClickListener(this);
        btnAlpha3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rotate_1:
                Animation ar1 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//                ivImage.startAnimation(ar1);
                RotateAnimation ra1 = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                ra1.setDuration(2000);
                ra1.setFillAfter(true);
                ra1.setInterpolator(new AccelerateDecelerateInterpolator());
                ra1.setRepeatCount(1);
                ra1.setRepeatMode(Animation.REVERSE);
                ivImage.startAnimation(ra1);
                break;
            case R.id.btn_scale_1:
                Animation as1 = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
//                ivImage.startAnimation(as1);
                ScaleAnimation sa1 = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation
                        .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sa1.setDuration(3000);
                sa1.setFillAfter(true);
                sa1.setRepeatMode(Animation.REVERSE);
                sa1.setRepeatCount(3);
                sa1.setInterpolator(new AccelerateDecelerateInterpolator());
                ivImage.startAnimation(sa1);
                break;
            case R.id.btn_translate_1:
                Animation at1 = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
//                ivImage.startAnimation(at1);
                TranslateAnimation ta1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation
                        .ABSOLUTE, 500, 0, 0, 0, 500);
                ta1.setDuration(2000);
                ta1.setFillAfter(true);
                ta1.setInterpolator(new AccelerateDecelerateInterpolator());
                ivImage.startAnimation(ta1);
                break;
            case R.id.btn_alpha_1:
                Animation aa1 = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
//                ivImage.startAnimation(aa1);
                AlphaAnimation a1 = new AlphaAnimation(1, 0.5f);
                a1.setDuration(2000);
                a1.setFillAfter(true);
                a1.setRepeatMode(Animation.REVERSE);
                a1.setRepeatCount(5);
                ivImage.startAnimation(a1);
                break;
            case R.id.btn_rotate_2:
//                ObjectAnimator oa = ObjectAnimator.ofFloat(ivImage,"rotation",0,360,180);
//                oa.setDuration(2000);
//                oa.start();
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_rotate_oa);
                animator.setTarget(ivImage);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
                break;
            case R.id.btn_scale_2:
                ObjectAnimator asX2 = ObjectAnimator.ofFloat(ivImage, "scaleX", 1, 2, 1);
                ObjectAnimator asY2 = ObjectAnimator.ofFloat(ivImage, "scaleY", 1, 2, 1);
                AnimatorSet as2 = new AnimatorSet();
                as2.playTogether(asX2, asY2);
                as2.setDuration(4000);
                as2.start();
                break;
            case R.id.btn_translate_2:
                ObjectAnimator at2 = ObjectAnimator.ofFloat(ivImage, "translationX", 0, 300);
                at2.setDuration(2000);
                at2.setInterpolator(new AccelerateDecelerateInterpolator());
                at2.setRepeatCount(3);
                at2.setRepeatMode(ValueAnimator.REVERSE);
                at2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Toast.makeText(AnimationActivity.this, "开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Toast.makeText(AnimationActivity.this, "重复", Toast.LENGTH_SHORT).show();
                    }
                });
                at2.start();
                break;
            case R.id.btn_alpha_2:
                ObjectAnimator aa2 = ObjectAnimator.ofFloat(ivImage, "alpha", 1, 0.3f, 1);
                aa2.setDuration(2000);
                aa2.setRepeatCount(3);
                aa2.start();
                break;
            case R.id.btn_rotate_3:
                Animator animator1 = AnimatorInflater.loadAnimator(this, R.animator.animator_set);
                animator1.setTarget(ivImage);
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Toast.makeText(AnimationActivity.this, "开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Toast.makeText(AnimationActivity.this, "重复", Toast.LENGTH_SHORT).show();
                    }
                });
                animator1.start();
                break;
            case R.id.btn_scale_3:
             //matchParent 是-1
                int height = btnRotate3.getLayoutParams().height;//当前是wrapContent，height是-2
                ValueAnimator valueAnimator = ValueAnimator.ofInt(height,
                        500);//从200遍为500
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        btnRotate3.getLayoutParams().height = animatedValue;
                        btnRotate3.requestLayout();
                    }
                });
                valueAnimator.setDuration(5000);
                valueAnimator.start();

                break;
            case R.id.btn_translate_3:
                break;
            case R.id.btn_alpha_3:
                break;
            case R.id.iv_image:
                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}
