package com.example.mykotlin.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.view.View

/**
 * Created by Administrator on 2016/10/11 0011.
 * desc : 屏幕信息获取以及单位转换
 */
object UIUtils {

    fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    fun pxToDp(px: Float): Float {
        return px / Resources.getSystem().displayMetrics.density
    }

    fun spToPx(sp: Float): Float {
        return sp * Resources.getSystem().displayMetrics.scaledDensity
    }

    fun pxToSp(px: Float): Float {
        return px / Resources.getSystem().displayMetrics.scaledDensity
    }

    fun getDensity(): Float = Resources.getSystem().displayMetrics.density


    fun getStatusHeight(context: Context): Int {
        var statusBarHeight = -1
        //获取status_bar_height资源的ID
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { //根据资源ID获取响应的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun getViewMeasuredHeight(view: View): Int {
        calculateViewMeasure(view)
        return view.measuredHeight
    }

    fun getViewMeasuredWidth(view: View): Int {
        calculateViewMeasure(view)
        return view.measuredWidth
    }

    private fun calculateViewMeasure(view: View) {
        val w = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        val h = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        view.measure(w, h)
    }
}