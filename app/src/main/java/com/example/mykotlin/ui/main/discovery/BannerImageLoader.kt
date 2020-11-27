package com.example.mykotlin.ui.main.discovery

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.mykotlin.R
import com.example.mykotlin.common.simple.ImageOptions
import com.example.mykotlin.common.simple.loadImage
import com.example.mykotlin.model.bean.BannerInfo
import com.youth.banner.loader.ImageLoader

class BannerImageLoader(val fragment: Fragment) : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val imagePath = (path as? BannerInfo)?.imagePath
        imageView?.loadImage(fragment = fragment, url = imagePath, imageOptions = ImageOptions().apply {
                placeholder = R.color.color_8e8e8e
            })
    }

}