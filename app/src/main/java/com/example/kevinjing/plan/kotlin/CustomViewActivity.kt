package com.example.kevinjing.plan.kotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.kevinjing.plan.R

/**
 * Created by Kevin on 2018/11/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class CustomViewActivity : AppCompatActivity(), ToggleView.OnToggleClickListener {

    private var toggleView: ToggleView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        toggleView = findViewById(R.id.tv_toggle)
        toggleView!!.setOnToggleClickListener(this)
//        toggleView!!.setCheckedColor(Color.parseColor("#951555"))
//        toggleView!!.setChecked(false)
    }

    override fun onToggleClick(toggleState: Boolean) {
        Toast.makeText(this, if (toggleState) "kotlin\t开" else "kotlin\t关", Toast.LENGTH_SHORT).show()
    }

}