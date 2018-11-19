package com.example.kevinjing.plan.kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.kevinjing.plan.R
import com.example.kevinjing.plan.frame.ApkActivity
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * Created by Kevin on 2018/11/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class KotlinActivity : AppCompatActivity(), View.OnClickListener {

    private var btnCustomView: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        btnCustomView = findViewById(R.id.btn_custom_view)
        btnCustomView!!.setOnClickListener(this)
        btn_apk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_custom_view -> startActivity(Intent(this, CustomViewActivity::class.java))
            R.id.btn_apk -> startActivity(Intent(this, ApkActivity::class.java))
        }
    }
}