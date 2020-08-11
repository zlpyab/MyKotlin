package com.example.mykotlin.ui.main.home.hot

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class HotFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_hot

    companion object{
        fun newInstance() = HotFragment()
    }

}