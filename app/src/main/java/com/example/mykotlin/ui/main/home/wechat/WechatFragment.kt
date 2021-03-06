package com.example.mykotlin.ui.main.home.wechat

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.adapter.ArticleAdapter
import com.example.mykotlin.common.adapter.CategoryAdapter
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.util.ActivityHelper
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class WechatFragment : BaseVmFragment<WechatViewModel>(), ScrollToTop {

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initLayout() = R.layout.fragment_wechat

    override fun viewModelClass() = WechatViewModel::class.java

    lateinit var mAdapter: ArticleAdapter
    lateinit var categoryAdapter: CategoryAdapter

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.refreshWechatArticleList()
            }
        }
        mAdapter = ArticleAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreWechatArticleList()
            }
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to article))
            }
            setOnItemChildClickListener { _, view, position ->
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
        categoryAdapter = CategoryAdapter().apply {
            onCheckedListener = {
                mViewModel.refreshWechatArticleList(it)
            }
        }
        rvCategory.adapter = categoryAdapter

        list_reload.setOnClickListener {
            mViewModel.refreshWechatArticleList()
        }
        view_reload.setOnClickListener {
            mViewModel.getWechatCategory()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            categoryList.observe(viewLifecycleOwner, Observer {
                rvCategory.isGone = it.isEmpty()
                categoryAdapter.setList(it)
            })
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setList(it)
            })
            checkedCategory.observe(viewLifecycleOwner, Observer {
                categoryAdapter.check(it)
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
            reloadListState.observe(viewLifecycleOwner, Observer {
                list_reload.isVisible = it
            })
            reloadViewState.observe(viewLifecycleOwner, Observer {
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
        mViewModel.getWechatCategory()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

}