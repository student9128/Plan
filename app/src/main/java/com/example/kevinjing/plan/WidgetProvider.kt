package com.example.kevinjing.plan

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

class WidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        // 接收开机和启动程序时候的广播时执行
        super.onReceive(context, intent)
        context.startService(Intent(context, TimerService::class.java))
    }

    override fun onEnabled(context: Context) {
        // Widget添加到屏幕时执行
        super.onEnabled(context)
        context.startService(Intent(context, TimerService::class.java))
    }

    override fun onDisabled(context: Context) {
        // 最后一次Widget从屏幕移除时执行
        super.onDisabled(context)
        context.stopService(Intent(context, TimerService::class.java))
    }
}
