package com.example.mykotlin.ui.activity

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.KeyboardUtils
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity
import com.example.mykotlin.main.MainActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import com.example.mykotlin.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录界面
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {


    override fun getLayoutId() =  R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java


    override fun initView() {
//        SessionUtils.getCellPhone()?.let {
//            et_phone.setText(SessionUtils.getCellPhone())
//            et_phone.setSelection(SessionUtils.getCellPhone().length)
//        }
//
//        bt_login.setOnClickListener {
//            val phone = et_phone.text.toString()
//            val psw = et_psw.text.toString()
//            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(psw)) {
//                Utils.showToast("账号密码不能为空")
//                return@setOnClickListener
//            }
//            KeyboardUtils.hideSoftInput(this)
//            mViewModel.login(phone, psw)
//        }
    }

    override fun observe() {
        mViewModel.run {
            loginData.observe(this@LoginActivity, Observer {
                Utils.showToast("登录成功")
                ActivityHelper.start(MainActivity::class.java)
                finish()
            })
        }
    }
}