package com.example.mykotlin.common.simple

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Created by zlp on 2020/8/14 0014.
 */

//拷贝至剪切板
fun Context.copyTextIntoClipboard(text : CharSequence , label : String = ""){
    if (text.isNullOrEmpty()) return
    val clip = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager ?: return
    clip.setPrimaryClip(ClipData.newPlainText(label, text))
}