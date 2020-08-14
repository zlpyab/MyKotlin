package com.example.mykotlin.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.mykotlin.R
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.simple.copyTextIntoClipboard
import com.example.mykotlin.common.simple.openExplore
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.util.Utils
import com.example.mykotlin.util.share
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_layout_action.*

/**
 * Created by zlp on 2020/8/14 0014.
 */
class ActionFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(article: Article): ActionFragment {
            return ActionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.key_data, article)
                }
            }
        }
    }

    var behavior: BottomSheetBehavior<View>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_layout_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            val article = getParcelable<Article>(Constants.key_data) ?: return@run
            ll_collect.visibility = if (article.id == 0) View.GONE else View.VISIBLE
            iv_collect.isSelected = article.collect
            tv_collect.text =
                getString(if (article.collect) R.string.cancel_collect else R.string.add_collect)
            ll_collect.setOnClickListener {
                var detailsActivity = (activity as? DetailsActivity) ?: return@setOnClickListener
                if (detailsActivity.checkLogin()) {
                    view.isSelected = !article.collect
                    detailsActivity.changeCollectState()
                    behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                } else {
                    view.postDelayed({ dismiss() }, 300)
                }
            }
            ll_share.setOnClickListener {
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                share(requireActivity(), getString(R.string.app_name), article.title + article.link)
            }
            ll_browse.setOnClickListener {
                openExplore(article.link)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
            ll_copy.setOnClickListener {
                context?.copyTextIntoClipboard(article.link)
                Utils.showToast(getString(R.string.copy_success))
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun show(manager: FragmentManager) {
        if (!this.isAdded) {
            super.show(manager, "ActionFragment")
        }
    }

}