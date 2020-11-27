package com.example.mykotlin.ui.main.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.adapter.ArticleAdapter
import com.example.mykotlin.common.adapter.CategoryAdapter
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.loadmore.CommonLoadMoreView
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.Category
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.fragment_system_pager.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Created by zlp on 2020/10/13 0013.
 */
class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {

    override fun initLayout() = R.layout.fragment_system_pager

    override fun viewModelClass() = SystemPagerViewModel::class.java

    companion object {
        private const val CATEGORY_LIST = "CATEGORY_LIST"
        fun newInstance(categoryList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }

    lateinit var mAdapter: ArticleAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: ArrayList<Category>
    var currentPosition = 0


    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.refreshArticleList(categoryList[currentPosition].id)
            }
        }
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        currentPosition = 0
        categoryAdapter = CategoryAdapter().apply {
            setList(categoryList)
            onCheckedListener = {
                currentPosition = it
                mViewModel.refreshArticleList(categoryList[currentPosition].id)
            }
        }
        rv_category.adapter = categoryAdapter

        mAdapter = ArticleAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList(categoryList[currentPosition].id)
            }
            setOnItemClickListener { _, _, position ->
                var article = mAdapter.data[position]
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to article))
            }
            setOnItemChildClickListener { _, view, position ->
                var article = mAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.unCollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
        }
        recyclerView.adapter = mAdapter

        bt_reload.setOnClickListener {
            mViewModel.refreshArticleList(categoryList[currentPosition].id)
        }
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList(categoryList[currentPosition].id)
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setList(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                bt_reload.isVisible = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.Complete -> mAdapter.loadMoreModule.loadMoreComplete()
                    LoadMoreStatus.Fail -> mAdapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.End -> mAdapter.loadMoreModule.loadMoreEnd()
                }
            })
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
            Utils.d("--pp--")
            mViewModel.updateItemCollectState(it)
        })
        Bus.observe<String>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
            mViewModel.updateListCollectState()
        })
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }
}