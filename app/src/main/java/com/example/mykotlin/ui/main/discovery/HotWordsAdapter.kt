package com.example.mykotlin.ui.main.discovery

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mykotlin.R
import com.example.mykotlin.model.bean.HotWords
import kotlinx.android.synthetic.main.item_hot_word.view.*

/**
 * Created by zlp on 2020/10/13 0013.
 */
class HotWordsAdapter : BaseQuickAdapter<HotWords,BaseViewHolder>(R.layout.item_hot_word){
    override fun convert(holder: BaseViewHolder, item: HotWords) {
        holder.itemView.tvName.text = item.name
    }
}