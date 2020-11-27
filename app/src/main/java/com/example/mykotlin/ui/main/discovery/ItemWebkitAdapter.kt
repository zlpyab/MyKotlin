package com.example.mykotlin.ui.main.discovery

import android.view.LayoutInflater
import android.view.View
import com.example.mykotlin.R
import com.example.mykotlin.model.bean.Webkits
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * Created by zlp on 2020/10/13 0013.
 */
class ItemWebkitAdapter(private val list:List<Webkits>) : TagAdapter<Webkits>(list) {
    override fun getView(parent: FlowLayout?, position: Int, t: Webkits?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_tag, parent, false).apply {
                tv_tag.text = list[position].name
            }
    }
}