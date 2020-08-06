package com.example.mykotlin.util

import android.app.Activity
import android.content.Intent
import com.example.mykotlin.main.MainActivity
import com.example.mykotlin.ui.activity.LoginActivity

/**
 * Created by zlp on 2020/7/28 0028.
 */
class ActivityJumpUtils {

    companion object {

        fun jumpToLoginActivity(activity: Activity) {
            var intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }

        fun jumpToMainActivity(activity: Activity) {
            var intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}