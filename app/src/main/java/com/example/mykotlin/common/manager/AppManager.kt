package com.example.mykotlin.common.manager

import android.app.Activity
import com.blankj.utilcode.util.KeyboardUtils
import java.util.*

/**
 * Created by zlp on 2020/7/28 0028.
 * 管理类
 */
class AppManager {

    private val mActivities = Stack<Activity>()

    companion object{
        private object Holder {
            val INSTANCE = AppManager()
        }

        fun getInstance(): AppManager{
            return Holder.INSTANCE
        }
    }

    fun addActivity(activity: Activity) {
        mActivities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        KeyboardUtils.hideSoftInput(activity)
        activity.finish()
        mActivities.remove(activity)
    }

    fun removeAllActivity() {
        for (activity in mActivities) {
            KeyboardUtils.hideSoftInput(activity)
            activity.finish()
        }
        mActivities.clear()
    }

    fun <T : Activity?> hasActivity(tClass: Class<T>): Boolean {
        for (activity in mActivities) {
            if (tClass.name == activity.javaClass.name) {
                return !activity.isDestroyed || !activity.isFinishing
            }
        }
        return false
    }
}