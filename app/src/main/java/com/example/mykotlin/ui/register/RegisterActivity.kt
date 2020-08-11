package com.example.mykotlin.ui.register

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.util.ActivityHelper
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseVmActivity<RegisterViewModel>() {

    override fun getLayoutId() = R.layout.activity_register

    override fun viewModelClass() = RegisterViewModel::class.java

    override fun initView() {
        tietConfirmPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                bt_register.performClick()
                true
            }else{
                false
            }
        }
        bt_register.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            tilConfirmPassword.error = ""
            var account = tietAccount.text.toString()
            var psw = tietPassword.text.toString()
            var confirmPsw = tietConfirmPassword.text.toString()
            when{
                account.isEmpty() -> tilAccount.error = getString(R.string.account_not_empty)
                account.length < 3 -> tilAccount.error = getString(R.string.account_limit_three)
                psw.isEmpty() -> tilPassword.error = getString(R.string.psw_not_empty)
                psw.length < 3 -> tilPassword.error = getString(R.string.psw_limit_six)
                confirmPsw.isEmpty() -> tilConfirmPassword.error = getString(R.string.confirm_psw_not_empty)
                confirmPsw!=psw -> tilConfirmPassword.error = getString(R.string.psw_not_match)

                else ->mViewModel.register(account,psw,confirmPsw)
            }
        }

    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@RegisterActivity, Observer {
                if (it) showProgressDialog(R.string.doing_register) else hintProgressDialog()
            })
            registerResult.observe(this@RegisterActivity, Observer {
                if (it){
                    ActivityHelper.finish(LoginActivity::class.java,RegisterActivity::class.java)
                }
            })
        }
    }
}