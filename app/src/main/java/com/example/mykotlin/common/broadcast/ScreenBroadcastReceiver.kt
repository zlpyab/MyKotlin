package com.example.mykotlin.common.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mykotlin.App
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.simple.isTopActivity
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.psw.PswLockActivity
import com.example.mykotlin.ui.splash.SplashActivity
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/9/5 0005.
 */
class ScreenBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_USER_PRESENT -> {//解锁屏幕
                val isOpenLock = SessionUtils.isLock()
                if (!isOpenLock) { //如果手势功能处于关闭状态或者没有获取存储的页面则不会进入手势解锁页面
                    return
                }
                val topActivity = isTopActivity(context) //应用是否处于前台
                if (topActivity) {
                    var activityName = context.javaClass.simpleName
                    if (activityName == LoginActivity::class.java.simpleName
                        || activityName == PswLockActivity::class.java.simpleName
                        || activityName == SplashActivity::class.java.simpleName) {
                        return
                    }
                    Constants.isActive = true
                    Constants.isCheckPsw = true
                    context.startActivity(Intent(context, PswLockActivity::class.java)) //如果屏幕解锁时，应用处于前台则开启手势解锁页面

                }
            }
        }
    }
}