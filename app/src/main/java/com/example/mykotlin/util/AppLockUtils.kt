package com.example.mykotlin.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.mykotlin.App
import com.example.mykotlin.common.Constants
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.psw.PswLockActivity
import com.example.mykotlin.ui.splash.SplashActivity

/**
 * Created by zlp on 2020/9/6 0006.
 */
object AppLockUtils {

    lateinit var activity: Activity

    fun openGesture(context: Activity) {
        this.activity = context
        if (!Constants.isActive) {
            var isSetLock = SessionUtils.isLock()
            if (!isSetLock) {
                return
            }
            var className = activity.javaClass.simpleName
            if (className == SplashActivity::class.simpleName
                || className == LoginActivity::class.simpleName
                || className == PswLockActivity::class.java.simpleName) {
                return
            }
            Utils.d("-----openGesture-------")
            Constants.isActive = true
            Constants.isCheckPsw = true
            context.startActivity(Intent(activity, PswLockActivity::class.java))
        }
    }
}
