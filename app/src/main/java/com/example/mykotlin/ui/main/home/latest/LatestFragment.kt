package com.example.mykotlin.ui.main.home.latest

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class LatestFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_latest

    companion object{
        fun newInstance() = LatestFragment()
    }

}