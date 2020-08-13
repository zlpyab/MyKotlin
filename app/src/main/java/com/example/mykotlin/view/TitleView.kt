package com.example.mykotlin.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.mykotlin.R
import com.example.mykotlin.common.simple.htmlToSpanned
import com.example.mykotlin.util.ActivityHelper
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.view_title.view.*

/**
 * Created by zlp on 2020/8/13 0013.
 */
class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : FrameLayout(context, attrs, def) {

    lateinit var clazz: Class<out Activity>

    init {
        val styleable = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        val showMore = styleable.getBoolean(R.styleable.TitleView_showMore, true)
        styleable.recycle()

        inflate(context, R.layout.view_title, this)

        iv_more.visibility = if (showMore) View.VISIBLE else View.INVISIBLE

        iv_back.setOnClickListener {
            clazz?.let {
                ActivityHelper.finish(clazz)
            }
        }
    }


    fun bind(clazz: Class<out Activity>,str:String) {
        this.clazz = clazz
        if (!str.isNullOrEmpty())
            tv_title.text = str.htmlToSpanned()
        val activity = context as Activity
        ImmersionBar.with(activity).titleBar(R.id.ll_title).init()
    }

    fun setTitle(str:String?){
        if (!str.isNullOrEmpty())
            tv_title.text = str.htmlToSpanned()
    }
}