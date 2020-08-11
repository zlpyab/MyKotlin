package com.example.mykotlin.ui.main.home.square

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class SquareFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_square

    companion object{
        fun newInstance() = SquareFragment()
    }
}