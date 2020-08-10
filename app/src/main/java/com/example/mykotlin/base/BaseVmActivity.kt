package com.example.mykotlin.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.util.ActivityHelper

/**
 * Created by zlp on 2020/8/7 0007.
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }


    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 获取ViewModel的class
     */
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {
        // 登录失效，跳转登录页
        mViewModel.loginStatusInvalid.observe(this, Observer {
            if (it) {
                Bus.post(USER_LOGIN_STATE_CHANGED, false)
                ActivityHelper.start(LoginActivity::class.java)
            }
        })
    }

    /**
     * 数据初始化相关
     */
    open fun initView() {

    }


    /**
     * 懒加载数据
     */
    open fun initData() {

    }

    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return true
    }
}