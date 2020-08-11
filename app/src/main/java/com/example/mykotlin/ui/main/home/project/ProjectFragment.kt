package com.example.mykotlin.ui.main.home.project

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class ProjectFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_project

    companion object{
        fun newInstance() = ProjectFragment()
    }

}