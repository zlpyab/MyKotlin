package com.example.mykotlin.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment
import com.example.mykotlin.common.adapter.SimpleFragmentPagerAdapter
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.ui.main.MainActivity
import com.example.mykotlin.ui.main.home.hot.HotFragment
import com.example.mykotlin.ui.main.home.latest.LatestFragment
import com.example.mykotlin.ui.main.home.project.ProjectFragment
import com.example.mykotlin.ui.main.home.square.SquareFragment
import com.example.mykotlin.ui.main.home.wechat.WechatFragment
import com.google.android.material.appbar.AppBarLayout
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zlp on 2020/8/11 0011.
 * 首页
 */
class HomeFragment : BaseFragment(), ScrollToTop {

    private var currentOffset = 0
    private lateinit var mFragments: List<Fragment>

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initLayout() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragments = listOf(
            HotFragment.newInstance(),
            LatestFragment.newInstance(),
            SquareFragment.newInstance(),
            ProjectFragment.newInstance(),
            WechatFragment.newInstance()
        )
        val titles = listOf<CharSequence>(
            getString(R.string.hot),
            getString(R.string.latest),
            getString(R.string.square),
            getString(R.string.project),
            getString(R.string.wechat)
        )

        viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, mFragments, titles)
        viewPager.offscreenPageLimit = mFragments.size
        tabLayout.setupWithViewPager(viewPager)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (activity is MainActivity && currentOffset != verticalOffset) {
                (activity as MainActivity).animateBottomNavigationView(verticalOffset > currentOffset)
                currentOffset = verticalOffset
            }
        })

    }

    override fun scrollToTop() {
        if (!this::mFragments.isInitialized) return
        val currentFragment = mFragments[viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }
}