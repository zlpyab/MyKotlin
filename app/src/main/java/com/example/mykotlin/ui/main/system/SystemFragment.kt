package com.example.mykotlin.ui.main.system

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class SystemFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_system

    companion object{
        fun newInstance() = SystemFragment()
    }
}