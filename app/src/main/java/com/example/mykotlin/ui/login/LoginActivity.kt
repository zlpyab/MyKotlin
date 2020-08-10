package com.example.mykotlin.ui.login

import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录界面
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {


    override fun getLayoutId() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView() {
        tietPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_GO) {
                bt_login.performClick()
                true
            } else {
                false
            }
        }
        bt_login.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            var account = tietAccount.text.toString()
            var psw = tietPassword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_not_empty)
                psw.isEmpty() -> tilPassword.error = getString(R.string.psw_not_empty)

                else -> mViewModel.login(account, psw)

            }
        }
    }

    override fun observe() {
        super.observe()
       mViewModel.run {
           submitting.observe(this@LoginActivity, Observer {
               if (it) showProgressDialog(R.string.doing_login) else hintProgressDialog()
           })
           loginResult.observe(this@LoginActivity, Observer {
               if (it){
                   ActivityHelper.finish(LoginActivity::class.java)
               }
           })
       }
    }
}