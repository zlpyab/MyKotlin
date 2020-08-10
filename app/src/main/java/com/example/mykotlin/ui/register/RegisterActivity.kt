package com.example.mykotlin.ui.register

import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity

class RegisterActivity : BaseVmActivity<RegisterViewModel>() {

    override fun getLayoutId() = R.layout.activity_register

    override fun viewModelClass() = RegisterViewModel::class.java

    override fun initView() {

    }

    override fun observe() {
        super.observe()
    }
}