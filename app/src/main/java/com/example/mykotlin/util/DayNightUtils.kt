package com.example.mykotlin.util

import android.content.Context
import android.content.res.Configuration.*
import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by zlp on 2020/9/5 0005.
 */

fun isNightMode(context: Context): Boolean {
    var mode = context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    return mode == UI_MODE_NIGHT_YES
}

fun setNightMode(isNightMode: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
}