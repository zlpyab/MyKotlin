package com.example.mykotlin.ui.activity

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.KeyboardUtils
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseActivity
import com.example.mykotlin.util.ActivityJumpUtils
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import com.example.mykotlin.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initView() {
        SessionUtils.getCellPhone()?.let {
            et_phone.setText(SessionUtils.getCellPhone())
            et_phone.setSelection(SessionUtils.getCellPhone().length)
        }
    }

    override fun setListener() {
        bt_login.setOnClickListener {
            val phone = et_phone.text.toString()
            val psw = et_psw.text.toString()
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(psw)) {
                Utils.showToast("账号密码不能为空")
                return@setOnClickListener
            }
            KeyboardUtils.hideSoftInput(this)
            viewModel.login(phone, psw)
        }

        viewModel.loginData.observe(this, Observer {
            Utils.showToast("登录成功")
            ActivityJumpUtils.jumpToMainActivity(this)
            finish()

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
}