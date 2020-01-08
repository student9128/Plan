package com.example.kevinjing.plan

import android.annotation.SuppressLint
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class TimerService : Service() {

    private lateinit var rv: RemoteViews

    private lateinit var manager: AppWidgetManager

    // 定义定时器
    private lateinit var timer: Timer

    // 定义格式化日期时间
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("HH : mm-MM / dd   E")

    override fun onCreate() {
        super.onCreate()

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                updateViews()
            }
        }, 0, 1000)
    }

    private fun updateViews() {

        rv = RemoteViews(packageName, R.layout.time_widget)
        val cn = ComponentName(applicationContext, WidgetProvider::class.java)
        manager = AppWidgetManager.getInstance(applicationContext)

        val str = sdf.format(Date())
        val timeStr = str.substring(0, 7)
        rv.setTextViewText(R.id.appwidget_text, timeStr)
        manager.updateAppWidget(cn, rv)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }
}
