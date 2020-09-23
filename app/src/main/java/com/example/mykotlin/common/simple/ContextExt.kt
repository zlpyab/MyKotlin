package com.example.mykotlin.common.simple

import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.psw.PswLockActivity

/**
 * Created by zlp on 2020/8/14 0014.
 */

//拷贝至剪切板
fun Context.copyTextIntoClipboard(text : CharSequence , label : String = ""){
    if (text.isNullOrEmpty()) return
    val clip = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager ?: return
    clip.setPrimaryClip(ClipData.newPlainText(label, text))
}

//app是否在前台
fun isTopActivity(context: Context): Boolean {
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val tasks = am.getRunningTasks(1)
    if (tasks.isNotEmpty()) {
        val topActivity = tasks[0].topActivity
        if (topActivity?.packageName == context.packageName) {
            return true
        }
    }
    return false
}