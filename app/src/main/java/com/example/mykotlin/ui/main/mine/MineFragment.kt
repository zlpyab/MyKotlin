package com.example.mykotlin.ui.main.mine

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class MineFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_mine

    companion object{
        fun newInstance() = MineFragment()
    }
}