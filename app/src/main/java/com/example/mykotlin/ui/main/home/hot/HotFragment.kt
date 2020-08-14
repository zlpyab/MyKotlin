package com.example.mykotlin.ui.main.home.hot

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.adapter.ArticleAdapter
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class HotFragment : BaseVmFragment<HotViewModel>(), ScrollToTop {

    override fun initLayout() = R.layout.fragment_hot

    override fun viewModelClass() = HotViewModel::class.java

    companion object {
        fun newInstance() = HotFragment()
    }

    lateinit var mAdapter: ArticleAdapter


    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.refreshArticleList()
            }
        }

        mAdapter = ArticleAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList()
            }
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to article))
            }
            setOnItemChildClickListener { adapter, view, position ->
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    val article = mAdapter.data[position]
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
            mViewModel.refreshArticleList()
        }
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
                view_reload.isVisible = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETE -> mAdapter.loadMoreModule.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreModule.loadMoreEnd()
                    else -> return@Observer
                }
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
            mViewModel.updateListCollectState()
        })
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
            mViewModel.updateItemCollectState(it)
        })
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}