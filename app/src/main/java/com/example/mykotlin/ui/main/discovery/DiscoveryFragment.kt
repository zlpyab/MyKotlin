package com.example.mykotlin.ui.main.discovery

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class DiscoveryFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_discovery

    companion object{
        fun newInstance() = DiscoveryFragment()
    }
}