package com.example.kevinjing.plan.frame

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.kevinjing.plan.R
import kotlinx.android.synthetic.main.activity_activity_manager.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kevin on 2018/11/15<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ActivityManagerActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG: String = "AMActivity--"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_manager)
        val attributes = window.attributes
        attributes.screenBrightness=0.8f//调节亮度
        window.attributes=attributes
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)//屏幕长亮
        btn_getInfo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_getInfo -> {
                val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val memoryInfo: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
                am.getMemoryInfo(memoryInfo)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val appTasks = am.appTasks
                } else {
                    var recentTasks: List<ActivityManager.RecentTaskInfo> = am.getRecentTasks(Int.MAX_VALUE, 1)
                    if (recentTasks == null) {
                        Log.i(TAG, "is not on foreground")
                    } else {
                        if (recentTasks[0].baseIntent.toString().contains(packageName)) {
                            Log.i(TAG, "is on foreground---" + recentTasks[0].baseIntent.toString())
                        }
                    }
                }
                val pm = packageManager
                val it = Intent(Intent.ACTION_MAIN)
                it.addCategory(Intent.CATEGORY_HOME)
                val queryIntentActivities = pm.queryIntentActivities(it, 0)
                if (queryIntentActivities.size > 0) {
                    for (i in queryIntentActivities.indices) {
                        val packageName = queryIntentActivities[i].activityInfo.packageName
                        Log.i(TAG, "packageName=$packageName\n")
                    }
                }

                val memoryClass = am.memoryClass
                val largeMemoryClass = am.largeMemoryClass
                val launcherLargeIconSize = am.launcherLargeIconSize
                val launcherLargeIconDensity = am.launcherLargeIconDensity
                val lowRamDevice = am.isLowRamDevice

                tv_info.text = ("memoryClass=$memoryClass, largeMemoryClass=$largeMemoryClass," +
                        "launcherLargeIconSize=$launcherLargeIconSize, launcherLargeIconDensity$launcherLargeIconDensity," +
                        "lowRamDevice=$lowRamDevice")
                if (Build.VERSION.SDK_INT >= 23) {
                    initManager()
                    initAppTask()
                }
                val runningAppProcesses = am.runningAppProcesses
                for (i in runningAppProcesses) {
                    val i1 = i.importanceReasonComponent
                    if (i1 != null) {
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--getClassName:" + i1.className);
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--getPackageName:" + i1.packageName);
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--getShortClassName:" + i1.shortClassName);
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--flattenToShortString:" + i1.flattenToShortString());
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--flattenToString:" + i1.flattenToString());
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--toShortString:" + i1.toShortString());
                        Log.d(TAG, "runningAppProcessInfo--importanceReasonComponent--describeContents:" + i1.describeContents());
                        Log.d(TAG, " \n");
                    }
                    Log.d(TAG, "processName:" + i.processName);
                    Log.d(TAG, "describeContents():" + i.describeContents());
                    Log.d(TAG, "importance:" + i.importance);
                    Log.d(TAG, "importanceReasonCode:" + i.importanceReasonCode);
                    Log.d(TAG, "importanceReasonPid:" + i.importanceReasonPid);
                    Log.d(TAG, "lastTrimLevel:" + i.lastTrimLevel);
                    Log.d(TAG, "lru:" + i.lru);
                    Log.d(TAG, "uid:" + i.uid);
                    for (x in i.pkgList) {
                        Log.i(TAG, "runningAppProcessInfo.pkgList==$x")
                    }
                }

                val runningServices = am.getRunningServices(Int.MAX_VALUE)
                for (i in runningServices) {
                    val componentName1 = i.service

                    if (componentName1 != null) {
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--getClassName:" + componentName1.getClassName());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--getPackageName:" + componentName1.getPackageName());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--getShortClassName:" + componentName1.getShortClassName());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--flattenToShortString:" + componentName1.flattenToShortString());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--flattenToString:" + componentName1.flattenToString());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--toShortString:" + componentName1.toShortString());
                        Log.d(TAG, "runningServiceInfo--ComponentName--service--describeContents:" + componentName1.describeContents());
                        Log.d(TAG, " \n");
                    }
                    Log.d(TAG, "clientPackage:" + i.clientPackage);
                    Log.d(TAG, "clientCount:" + i.clientCount);
                    Log.d(TAG, "clientLabel:" + i.clientLabel);
                    Log.d(TAG, "activeSince:" + i.activeSince);
                    Log.d(TAG, "process:" + i.process);
                    Log.d(TAG, "crashCount:" + i.crashCount);
                    Log.d(TAG, "describeContents:" + i.describeContents());
                    Log.d(TAG, "flags:" + i.flags);
                    Log.d(TAG, "pid:" + i.pid);
                    Log.d(TAG, "restarting:" + i.restarting);
                    Log.d(TAG, "started:" + i.started);
                    Log.d(TAG, "uid:" + i.uid);
                    val lastActivityTime = i.lastActivityTime
                    val date = Date(lastActivityTime)
                    val sdf = SimpleDateFormat("yyyy年MM月DD日 HH:mm:ss")
                    val format = sdf.format(date)

                    Log.d(TAG, "lastActivityTime:$format");
                    Log.d(TAG, " \n");
                }

                //Task description
                val label = resources.getString(applicationInfo.labelRes)
                val color = resources.getColor(R.color.colorPrimary)
                val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                val taskDescription = ActivityManager.TaskDescription(label, icon, color)
                setTaskDescription(taskDescription)
                Log.d(TAG, "getLabel:" + taskDescription.label);
                Log.d(TAG, "describeContents():" + taskDescription.describeContents());
                Log.d(TAG, "getIcon()==null" + (taskDescription.icon == null));
                Log.d(TAG, "getPrimaryColor:" + taskDescription.primaryColor);

                val processesInErrorState = am.processesInErrorState
                if (processesInErrorState != null) {
                    for (i in processesInErrorState) {
                        Log.d(TAG, "longMsg:" + i.longMsg);
                        Log.d(TAG, "shortMsg:" + i.shortMsg);
                        Log.d(TAG, "processName:" + i.processName);
                        Log.d(TAG, "stackTrace:" + i.stackTrace);
                        Log.d(TAG, "tag:" + i.tag);
                        Log.d(TAG, "condition:" + i.condition);
                        Log.d(TAG, "describeContents():" + i.describeContents());
                        Log.d(TAG, "pid:" + i.pid);
                        Log.d(TAG, "uid:" + i.uid);
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initManager() {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val lockTaskModeState = am.lockTaskModeState
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initAppTask() {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val size = am.appTasks.size
        val appTasks = am.appTasks
        for (i in appTasks.indices) {
            val taskInfo = appTasks[i].taskInfo
            val oa = taskInfo.origActivity
            if (oa != null) {
                Log.d(TAG, "origActivity--ComponentName--getClassName:" + oa.className);
                Log.d(TAG, "origActivity--ComponentName--getPackageName:" + oa.packageName);
                Log.d(TAG, "origActivity--ComponentName--getShortClassName:" + oa.shortClassName);
                Log.d(TAG, "origActivity--ComponentName--flattenToShortString:" + oa.flattenToShortString());
                Log.d(TAG, "origActivity--ComponentName--flattenToString:" + oa.flattenToString());
                Log.d(TAG, "origActivity--ComponentName--toShortString:" + oa.toShortString());
                Log.d(TAG, "origActivity--ComponentName--describeContents:" + oa.describeContents());

            }
            if (Build.VERSION.SDK_INT > 23) {
                recentTaskInfo(taskInfo)
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun recentTaskInfo(taskInfo: ActivityManager.RecentTaskInfo) {
        val ba = taskInfo.baseActivity

        Log.d(TAG, "baseActivity--ComponentName--getClassName:" + ba.className);
        Log.d(TAG, "baseActivity--ComponentName--getPackageName:" + ba.packageName);
        Log.d(TAG, "baseActivity--ComponentName--getShortClassName:" + ba.shortClassName);
        Log.d(TAG, "baseActivity--ComponentName--flattenToShortString:" + ba.flattenToShortString());
        Log.d(TAG, "baseActivity--ComponentName--flattenToString:" + ba.flattenToString());
        Log.d(TAG, "baseActivity--ComponentName--toShortString:" + ba.toShortString());
        Log.d(TAG, "baseActivity--ComponentName--describeContents:" + ba.describeContents());

        val ta = taskInfo.topActivity
        Log.d(TAG, "origActivity--ComponentName--getClassName:" + ta.className);
        Log.d(TAG, "origActivity--ComponentName--getPackageName:" + ta.packageName);
        Log.d(TAG, "origActivity--ComponentName--getShortClassName:" + ta.shortClassName);
        Log.d(TAG, "origActivity--ComponentName--flattenToShortString:" + ta.flattenToShortString());
        Log.d(TAG, "origActivity--ComponentName--flattenToString:" + ta.flattenToString());
        Log.d(TAG, "origActivity--ComponentName--toShortString:" + ta.toShortString());
        Log.d(TAG, "origActivity--ComponentName--describeContents:" + ta.describeContents());

    }
}
