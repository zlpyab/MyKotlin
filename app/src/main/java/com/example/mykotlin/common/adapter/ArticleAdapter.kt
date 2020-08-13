package com.example.mykotlin.common.adapter

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mykotlin.R
import com.example.mykotlin.common.simple.htmlToSpanned
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * Created by zlp on 2020/8/12 0012.
 */
class ArticleAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article),
    LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.run {
            itemView.run {
                tv_author.text = when {
                    !item.author.isNullOrEmpty() -> item.author
                    !item.shareUser.isNullOrEmpty() -> item.shareUser
                    else -> context.getString(R.string.anonymous)
                }
                tv_top.isVisible = item.top
                tv_new.isVisible = item.fresh && !item.top
                tv_tag.isVisible = if (item.tags.isNotEmpty()) {
                    tv_tag.text = item.tags[0].name
                    true
                } else {
                    false
                }
                tv_chapter.text = when {
                    !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                    !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                        "${item.superChapterName.htmlToSpanned()}"
                    item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        "${item.chapterName}"
                    else -> ""
                }
                tv_title.text = item.title.htmlToSpanned()
                tv_des.text = item.desc.htmlToSpanned()
                tv_des.isGone = item.desc.isNullOrEmpty()
                tv_time.text = item.niceDate
                iv_collect.isSelected = item.collect
            }
        }
    }
}