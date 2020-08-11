package com.example.mykotlin.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.util.ActivityHelper

/**
 * Created by zlp on 2020/8/11 0011.
 */
abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }


    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }


    /**
     * 初始化viewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 获取viewModel的class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {
        // 登录失效，跳转登录页
        mViewModel.loginStatusInvalid.observe(viewLifecycleOwner, Observer {
            if (it) {
                Bus.post(USER_LOGIN_STATE_CHANGED, false)
                ActivityHelper.start(LoginActivity::class.java)
            }
        })
    }


    /**
     * View初始化相关
     */
    open fun initView() {


    }

    /**
     * 数据初始化相关
     */
    open fun initData() {


    }

    /**
     * 懒加载数据
     */
    open fun lazyLoadData() {

    }

    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (isLogin()) {
            then?.invoke()
            true
        } else {
            ActivityHelper.start(LoginActivity::class.java)
            false
        }
    }

}