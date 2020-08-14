package com.example.mykotlin.common.simple

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

/**
 * Created by zlp on 2020/8/14 0014.
 */

fun Fragment.openExplore(link : String){
    startActivity(Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(link)
    })
}