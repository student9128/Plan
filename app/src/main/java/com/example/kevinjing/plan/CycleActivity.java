package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

import com.rocky.mediaplaysurface.surfaceview.EasyVideo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2019-12-04<br/>
 * Blog:http://student9128.top/
 * 公众号：竺小竹
 * Describe:<br/>
 */
public class CycleActivity extends AppCompatActivity {
    @BindView(R.id.ll_container)
    LinearLayout linearLayout;
    @BindView(R.id.video)
    EasyVideo video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_pic);
        ButterKnife.bind(this);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.i("CycleActivity", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
//        //widthPixels = 1440, heightPixels = 2768
//        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(widthPixels, heightPixels);
//        ll.setLayoutDirection(LinearLayout.HORIZONTAL);
//        ImageView imageView = new ImageView(this);
////        linearLayout.addView(imageView);
//        ImageView imageView2 = new ImageView(this);
//        linearLayout.addView(imageView2);
//        ImageView imageView3 = new ImageView(this);
////        linearLayout.addView(imageView3);
//        imageView.setLayoutParams(ll);
////        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView2.setLayoutParams(ll);
////        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView3.setLayoutParams(ll);
//        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(R.drawable.ic_test);
//        imageView2.setImageResource(R.drawable.ic_launcher_background);
//        imageView3.setImageResource(R.drawable.ic_test);
//        ObjectAnimator at = ObjectAnimator.ofFloat(imageView2, "translationX", 0, widthPixels);
//        ObjectAnimator at2 = ObjectAnimator.ofFloat(imageView, "translationX", -widthPixels, widthPixels);
//        at2.setDuration(4000);
//        at2.setInterpolator(new LinearInterpolator());
//        at.setDuration(2000);
//        at.setInterpolator(new LinearInterpolator());
//        at.start();
        EasyVideo v = findViewById(R.id.video);
        v.loadVideo("http://fs.mv.web.kugou.com/201912051657/80431c382060035fb5c5cbbe2b4b10cb/G168/M04/03/03/SIcBAF0S2l6AcudBBje74CZFDcI800.mp4");


    }
}
