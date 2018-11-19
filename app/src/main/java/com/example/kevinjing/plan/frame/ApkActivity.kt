package com.example.kevinjing.plan.frame

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kevinjing.plan.R
import kotlinx.android.synthetic.main.activity_apk.*
import java.io.File
import java.time.temporal.TemporalAccessor
import java.util.jar.Manifest

/**
 * Created by Kevin on 2018/11/15<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ApkActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apk)
        btn_check.setOnClickListener(this)
        btn_install.setOnClickListener(this)
        btn_check_uninstalled.setOnClickListener(this)
        btn_uninstall.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_check -> {
                var i = checkApkIsInstalled("com.bankcomm.ichart")
                Toast.makeText(this, if (i) {
                    "已安装"
                } else {
                    "未安装"
                }, Toast.LENGTH_SHORT).show()
            }
            R.id.btn_install -> {
                if (checkApkIsInstalled("com.bankcomm.ichart")) {
                    Toast.makeText(this, "已安装", Toast.LENGTH_SHORT).show()
                } else {

                    installApk("Damt.apk")
                }
            }
            R.id.btn_check_uninstalled -> {
                //扫描所以的apk,判断是否安装，未安装展示出来
            }
            R.id.btn_uninstall -> {
                //代码卸载apk
            }
        }
    }

    /**
     * 检查apk是否安装
     */
    private fun checkApkIsInstalled(packageName: String): Boolean {
        var isInstalled = false;
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES)
            val activities = packageInfo.activities
            if (activities == null) {
                Log.i("ApkActivity", "Activites==$activities")
            } else {

                Log.i("ApkActivity", "Activites is not empty")
            }

            if (packageInfo.activities != null && packageInfo.activities.isNotEmpty()) {
                isInstalled = true
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return isInstalled
    }

    /**
     * 安装apk
     */
    private fun installApk(apkName: String) {
        val f = File(Environment.getExternalStorageDirectory().toString() + File.separator + "Download")
        if (f.exists()) {
            Toast.makeText(this, "exists", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "can't find the directory", Toast.LENGTH_SHORT).show()

        }
        var fileNam = Environment.getExternalStorageDirectory().toString() + File.separator + "test" + File.separator + "Download" + File.separator + apkName
        var fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkName
        when {
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N -> installNApk(apkName)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> checkOreo(apkName)
            else -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setDataAndType(Uri.fromFile(File(fileName)), "application/vnd.android.package-archive")
                startActivity(intent)
            }
        }
    }

    /**
     * 安装在N以上版本的手机
     */
    private fun installNApk(apkName: String) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.REQUEST_INSTALL_PACKAGES), 1000)
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivityForResult(intent, 0)//去设置未安装界面
            }
        } else {
//            var fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkName
            var fileName =  Environment.getExternalStorageDirectory().toString() + File.separator + "Download" + File.separator + apkName
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(fileName)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            var contentUri = FileProvider.getUriForFile(this, "com.example.kevinjing.plan.fileprovider", file)
            val path = contentUri.path
            Log.i("ApkActivity", "需要安装的路径=====$path")
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
//            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
            startActivity(intent)
        }
    }

    fun getRes(packageName: String) {
        if (checkApkIsInstalled(packageName)) {
            val context = createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
//            context.resources.getDrawable()

        }
    }

    fun startApk(packageName: String) {
        val context = createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
        val clazz = context.classLoader.loadClass("$packageName.SplashActivity")
        startActivity(Intent(this, clazz))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkOreo(apkName: String) {
        if (packageManager.canRequestPackageInstalls()) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.REQUEST_INSTALL_PACKAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
//                    requestPermissions(arrayOf(android.Manifest.permission.REQUEST_INSTALL_PACKAGES), 1)
                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityForResult(intent, 0)//去设置未安装界面
                } else {
//                    requestPermissions(arrayOf(android.Manifest.permission.REQUEST_INSTALL_PACKAGES), 1)
//                    Toast.makeText(this, "do some thing", Toast.LENGTH_SHORT).show()
                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityForResult(intent, 0)//去设置未安装界面
                }
            } else {
                //安装
//                installApk("Damt.apk")
                installNApk(apkName)
            }
        } else {
            startActivityForResult(Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES), 0)//去设置未安装界面
        }

//
//        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED || !packageManager.canRequestPackageInstalls()
//                } else {
//                    ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
//                }) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//            }
//        } else {
//            //直接执行操作
//            when (requestCode) {
//                REQUEST_INSTALL_PACKAGES -> //执行安装操作
//            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (Build.VERSION.SDK_INT >= 26) {
                    installNApk("Damt.apk")
                    val apkName = "Damt.apk"
//                    var fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkName
                    var fileName = Environment.getExternalStorageDirectory().toString() + File.separator + "Download" + File.separator + apkName
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    val file = File(fileName)
                    if (file.exists()) {
                        Log.i("ApkActivity", "file is exists")
                    } else {
                        Log.i("ApkActivity", "file is not exists")

                    }
                    var contentUri = FileProvider.getUriForFile(this, "com.example.kevinjing.plan.fileprovider", file)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    startActivity(intent)

//                    val i = Intent(Intent.ACTION_INSTALL_PACKAGE)

                }
            }
//            1 -> {
//                installApk("Damt.apk")
//            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installNApk("Damt.apk")
                }
            }

//            1->{
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    installApk("Damt.apk")
//                }else{
//                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivityForResult(intent, 0)//去设置未安装界面
//                }
//            }
        }
    }


}