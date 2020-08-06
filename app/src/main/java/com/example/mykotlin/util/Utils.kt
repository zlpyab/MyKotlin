package com.example.mykotlin.util

import android.view.Gravity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * Created by zlp on 2020/7/31 0031.
 */
object Utils {
    public const val isDebug = true
    private const val TAG = "YiLog"

    fun d(msg: String) {
        LogUtils.dTag(TAG,msg)
    }

    fun showToast(msg : String){
        ToastUtils.setGravity(Gravity.CENTER,0,0)
        ToastUtils.showShort(msg)
    }
}