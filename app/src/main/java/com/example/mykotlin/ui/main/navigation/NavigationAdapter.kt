package com.example.mykotlin.ui.main.navigation

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mykotlin.R
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.Navigation
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.item_navigation.view.*
import kotlinx.android.synthetic.main.view_title.view.tv_title

/**
 * Created by zlp on 2020/10/12 0012.
 */
class NavigationAdapter : BaseQuickAdapter<Navigation,BaseViewHolder>(R.layout.item_navigation) {

    var onItemTagClickListener : ((articles : Article) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: Navigation) {

        holder.itemView.run {
            tv_title.text = item.name
            tagFlawLayout.adapter = ItemTagAdapter(item.articles)
            tagFlawLayout.setOnTagClickListener { _, position, _ ->
                onItemTagClickListener?.invoke(item.articles[position])
                true
            }
        }
    }

}