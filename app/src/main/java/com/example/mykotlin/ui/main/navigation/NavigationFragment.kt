package com.example.mykotlin.ui.main.navigation

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.ui.details.DetailsActivity
import com.example.mykotlin.ui.main.MainActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * 导航
 * Created by zlp on 2020/8/11 0011.
 */
class NavigationFragment : BaseVmFragment<NavigationViewModel>(), ScrollToTop {

    private var  currentPosition = 0
    lateinit var mAdapter: NavigationAdapter

    override fun initLayout() = R.layout.fragment_navigation

    override fun viewModelClass() = NavigationViewModel::class.java

    companion object {
        fun newInstance() = NavigationFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.color_0a5273)
            setProgressBackgroundColorSchemeResource(R.color.color_ffffff)
            setOnRefreshListener {
                mViewModel.getNavigations()
            }
        }
        mAdapter = NavigationAdapter().apply {
            onItemTagClickListener = {
                ActivityHelper.start(DetailsActivity::class.java, mapOf(Constants.key_data to it))
            }
        }
        recyclerView.adapter = mAdapter

        bt_reload.setOnClickListener {
            mViewModel.getNavigations()
        }


        recyclerView.setOnScrollChangeListener {  _, _, scrollY, _, oldScrollY ->
            if (activity is MainActivity && scrollY != oldScrollY){
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
            if (scrollY < oldScrollY){
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            var nextView = layoutManager.findViewByPosition(currentPosition+1)
            if (null != nextView){
                tvFloatTitle.y = if (nextView.top < tvFloatTitle.measuredHeight){
                    (nextView.top - tvFloatTitle.measuredHeight).toFloat()
                }else{
                    0f
                }
            }
            currentPosition = layoutManager.findFirstVisibleItemPosition()
            if (scrollY > oldScrollY){
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }

        }

    }

    override fun initData() {
        mViewModel.getNavigations()
    }


    override fun observe() {
        super.observe()
        mViewModel.apply {
            navigations.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone = it.isEmpty()
                tvFloatTitle.text = it[0].name
                mAdapter.setList(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                view_reload.isVisible = it

            })
        }
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }
}