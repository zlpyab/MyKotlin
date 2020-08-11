package com.example.mykotlin.ui.main.home.wechat

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment

/**
 * Created by zlp on 2020/8/11 0011.
 */
class WechatFragment : BaseFragment() {

    override fun initLayout() = R.layout.fragment_wechat

    companion object{
        fun newInstance() = WechatFragment()
    }

}