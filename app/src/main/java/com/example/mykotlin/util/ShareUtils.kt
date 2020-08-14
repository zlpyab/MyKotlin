package com.example.mykotlin.util

import android.app.Activity
import androidx.core.app.ShareCompat

/**
 * Created by zlp on 2020/8/14 0014.
 */
fun share(activity: Activity, title: String, content: String) {
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setSubject(title)
        .setText(content)
        .setChooserTitle(title)
        .startChooser()
}