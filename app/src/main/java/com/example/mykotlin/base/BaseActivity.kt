package com.example.mykotlin.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlin.common.manager.AppManager
import com.gyf.immersionbar.ImmersionBar

/**
 * Created by zlp on 2020/7/28 0028.
 */
open abstract class BaseActivity : AppCompatActivity() {

    var mActivity : Activity? = null
    var mImmersionBar : ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        mActivity = this
        setContentView(getLayoutId())

        //初始化沉浸式
        initImmersionBar()
        //初始化数据
        initData()
        //初始化view
        initView()
        //设置监听
        setListener()
    }

    private fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).apply {
            fitsSystemWindows(false)
            statusBarDarkFont(true)
            init()
        }
    }

    abstract fun getLayoutId(): Int

    open fun setListener() {
    }

    open  fun initView() {
    }

    open   fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }


}