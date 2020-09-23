package com.example.mykotlin.common.adapter

import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mykotlin.R
import com.example.mykotlin.common.simple.dpToPx
import com.example.mykotlin.common.simple.dpToPxInt
import com.example.mykotlin.model.bean.Category
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by zlp on 2020/8/17 0017.
 */
class CategoryAdapter : BaseQuickAdapter<Category,BaseViewHolder>(R.layout.item_category) {

    private var checkedPosition = 0
    var onCheckedListener : ((position : Int) ->Unit) ? = null

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.itemView.run {
             category.text = item.name
             category.isChecked = checkedPosition == holder.adapterPosition
             setOnClickListener {
                 var position = holder.adapterPosition
                 check(position)
                 onCheckedListener?.invoke(position)
             }
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = if (holder.adapterPosition == 0)  10.0f.dpToPxInt() else 0f.dpToPxInt()
            }
        }
    }

     fun check(position : Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }
}