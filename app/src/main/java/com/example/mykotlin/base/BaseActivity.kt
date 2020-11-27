package com.example.mykotlin.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlin.common.dialog.ProgressDialogFragment
import com.example.mykotlin.util.AppLockUtils
import com.gyf.immersionbar.ImmersionBar

/**
 * Created by zlp on 2020/7/28 0028.
 */
open abstract class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment

    protected lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        //初始化沉浸式
        initImmersionBar()
    }

    override fun onResume() {
        super.onResume()
        AppLockUtils.openGesture(this)
    }

    open fun getLayoutId() = 0

    private fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).apply {
            fitsSystemWindows(false)
            statusBarDarkFont(true)
            init()
        }
    }

    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    fun hintProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }
}