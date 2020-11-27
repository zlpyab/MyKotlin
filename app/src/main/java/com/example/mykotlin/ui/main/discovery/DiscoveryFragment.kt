package com.example.mykotlin.ui.main.discovery

import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.BannerInfo
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.ui.main.MainActivity
import com.example.mykotlin.util.ActivityHelper
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class DiscoveryFragment : BaseVmFragment<DiscoveryViewModel>(), ScrollToTop {

    lateinit var mAdapter: HotWordsAdapter

    override fun initLayout() = R.layout.fragment_discovery

    override fun viewModelClass() = DiscoveryViewModel::class.java

    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.getData()
            }
        }

        nestedScrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
        }

        mAdapter = HotWordsAdapter()
        rv_hot.adapter = mAdapter
    }

    override fun initData() {
        mViewModel.getData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            banners.observe(viewLifecycleOwner, Observer {
                setBanner(it)
            })
            hotWords.observe(viewLifecycleOwner, Observer {
                mAdapter.setList(it)
                tvHotWordTitle.isVisible = it.isNotEmpty()
            })
            webKits.observe(viewLifecycleOwner, Observer {
                tagFlowLayout.adapter = ItemWebkitAdapter(it)
                tvFrquently.isVisible = it.isNotEmpty()
                tagFlowLayout.setOnTagClickListener { _, position, _ ->
                    var tag = it[position]
                    ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to  Article(link = tag.link,title = tag.name)))
                    true
                }

            })
            refreshStates.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStates.observe(viewLifecycleOwner, Observer {
                view_reload.isVisible = it
            })
        }
    }

    private fun setBanner(banners: List<BannerInfo>) {
        bannerView.run {
            setBannerStyle(BannerConfig.NOT_INDICATOR)
            setImageLoader(BannerImageLoader(this@DiscoveryFragment))
            setImages(banners)
            setBannerAnimation(Transformer.CubeOut)
            start()
            setOnBannerListener {
                val banner = banners[it]
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to Article(title = banner.title, link = banner.url)))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bannerView.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bannerView.stopAutoPlay()
    }

    override fun scrollToTop() {
        nestedScrollView?.smoothScrollTo(0, 0)
    }
}