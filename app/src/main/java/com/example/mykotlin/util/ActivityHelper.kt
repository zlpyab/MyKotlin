package com.example.mykotlin.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.example.mykotlin.common.simple.ActivityLifecycleCallbacksAdapter
import com.example.mykotlin.common.simple.putExtras

/**
 * Created by zlp on 2020/7/28 0028.
 */
object ActivityHelper {

    private val activityList = mutableListOf<Activity>()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = { activity, _ ->
                activityList.add(activity)
            },
            onActivityDestroyed = { activity ->
                activityList.remove(activity)
            }
        ))
    }

    /**
     * 打开activity
     */
    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        var currentActivity = activityList[activityList.lastIndex]
        var intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    /**
     * finish 一个或多个activity
     */
    fun finish(vararg clazz: Class<out Activity>){
        activityList.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }

}