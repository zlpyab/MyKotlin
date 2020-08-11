package com.example.mykotlin.ui.main.navigation

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class NavigationFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_navigation

    companion object{
        fun newInstance() = NavigationFragment()
    }
}