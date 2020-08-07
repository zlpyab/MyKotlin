package com.example.mykotlin

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.Utils

/**
 * Created by zlp on 2020/7/25 0025.
 */
class App :Application(){

    companion object{
        lateinit var instance :App
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        ActivityHelper.init(instance)
        LogUtils.getConfig().setBorderSwitch(false).setLogSwitch(Utils.isDebug).setLogHeadSwitch(false)
    }
}