package com.example.mykotlin.common.manager

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by zlp on 2020/8/1 0001.
 */
class GlideManager {

    companion object {
        fun loadImg(url: String, img: ImageView, context: Context) {
            Glide.with(context)
                .load(url)
                .into(img)
        }
    }
}