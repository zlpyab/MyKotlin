package com.example.mykotlin.ui.main.home.square

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
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.util.ActivityHelper
import kotlinx.android.synthetic.main.fragment_latest.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class SquareFragment : BaseVmFragment<SquareViewModel>(), ScrollToTop {

    override fun initLayout() = R.layout.fragment_square

    companion object {
        fun newInstance() = SquareFragment()
    }

    override fun viewModelClass() = SquareViewModel::class.java

    lateinit var mAdapter: ArticleAdapter

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.refreshListData()
            }
        }
        mAdapter = ArticleAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreListData()
            }
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to article))
            }
            setOnItemChildClickListener { _, view, position ->
                if (view.id == R.id.iv_collect && isLogin()) {
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
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setList(it)
            })
            refreshState.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETE -> mAdapter.loadMoreModule.loadMoreComplete()
                    LoadMoreStatus.END -> mAdapter.loadMoreModule.loadMoreEnd()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreModule.loadMoreFail()
                    else -> return@Observer
                }
            })
            reloadState.observe(viewLifecycleOwner, Observer {
                view_reload.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
            mViewModel.updateListCollect()
        })
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
            mViewModel.updateItemCollect(it)
        })
    }

    override fun lazyLoadData() {
        mViewModel.refreshListData()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}