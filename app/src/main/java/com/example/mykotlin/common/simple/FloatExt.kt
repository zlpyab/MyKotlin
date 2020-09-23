package com.example.mykotlin.common.simple

import com.example.mykotlin.util.UIUtils

fun Float.dpToPx() = UIUtils.dpToPx(this)

fun Float.dpToPxInt() = UIUtils.dpToPx(this).toInt()

fun Float.pxToDp() = UIUtils.pxToDp(this)

fun Float.pxToDpInt() = UIUtils.pxToDp(this).toInt()