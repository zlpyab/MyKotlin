package com.example.mykotlin.ui.main.system

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.adapter.SimpleFragmentPagerAdapter
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.model.bean.Category
import com.example.mykotlin.ui.main.MainActivity
import com.example.mykotlin.ui.main.system.pager.SystemPagerFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class SystemFragment : BaseVmFragment<SystemViewModel>(), ScrollToTop {

    var currentOffset = 0
    val titles = mutableListOf<String>()
    val fragments = mutableListOf<SystemPagerFragment>()

    override fun initLayout() = R.layout.fragment_system

    override fun viewModelClass() = SystemViewModel::class.java

    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun initView() {
        bt_reload.setOnClickListener {
            mViewModel.getCategory()
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offest ->
            if (activity is MainActivity && offest != currentOffset) {
                (activity as MainActivity).animateBottomNavigationView(offest > currentOffset)
                currentOffset = offest
            }
        })

    }


    override fun initData() {
        mViewModel.getCategory()
    }

    override fun observe() {
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                tabLayout.isVisible = true
                viewPager.isVisible = true
                setup(it)
            })
            loadingStatus.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                view_reload.isVisible = it
            })
        }
    }

    private fun setup(categoryList: MutableList<Category>) {
        titles.clear()
        fragments.clear()
        categoryList.forEach {
            titles.add(it.name)
            fragments.add(SystemPagerFragment.newInstance(it.children))
        }
        viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, fragments, titles)
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun scrollToTop() {

    }
}