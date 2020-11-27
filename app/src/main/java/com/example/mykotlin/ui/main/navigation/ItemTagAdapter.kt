package com.example.mykotlin.ui.main.navigation

import android.view.LayoutInflater
import android.view.View
import com.example.mykotlin.R
import com.example.mykotlin.model.bean.Article
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * Created by zlp on 2020/10/12 0012.
 */
class ItemTagAdapter(private val articles : List<Article>) : TagAdapter<Article>(articles) {

    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {
       return LayoutInflater.from(parent?.context).inflate(R.layout.item_tag,parent,false).apply {
           tv_tag.text = articles[position].title
       }
    }
}