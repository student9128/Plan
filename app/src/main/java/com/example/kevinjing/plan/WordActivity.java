package com.example.kevinjing.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kevinjing.plan.custom.WordGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2020-01-08<br/>
 * Blog:http://student9128.top/
 * 公众号：竺小竹
 * Describe:<br/>
 */
public class WordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        WordGroupView word = findViewById(R.id.word_group);
        List<String> l = new ArrayList<>();
        l.add("Java");
        l.add("Python");
        l.add("Android");
        l.add("C++");
        l.add("Object C");
        l.add("Swift");
        l.add("JavaScript");
        l.add("Kotlin");
        l.add("Kotlin");
        l.add("HTML");
        l.add("CSS");
        l.add("JQuery");
        l.add("Vue");
        word.setWords(l);
    }
}
