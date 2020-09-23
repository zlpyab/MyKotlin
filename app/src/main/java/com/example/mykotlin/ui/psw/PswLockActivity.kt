package com.example.mykotlin.ui.psw

import android.app.AlertDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.SET_APP_LOCK
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.setting.SettingActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import kotlinx.android.synthetic.main.activity_details.titleView
import kotlinx.android.synthetic.main.activity_psw_lock.*

class PswLockActivity : BaseVmActivity<PswLockViewModel>() {

    override fun getLayoutId() = R.layout.activity_psw_lock

    override fun viewModelClass() = PswLockViewModel::class.java

    var isSetPsw = false
    override fun initView() {
        titleView.bind(PswLockActivity::class.java, getString(R.string.psw_lock))

        isSetPsw = intent.getBooleanExtra(Constants.key_data, false)
        if (isSetPsw) {
            tv_type.text = "设置密码"
            titleView.showBack(true)
        } else {
            tv_type.text = "验证密码"
            titleView.showBack(false)
        }
    }

    override fun observe() {
        super.observe()

        //忘记密码
        tv_forget_psw.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("忘记密码？退出登录后密码重置")
                .setPositiveButton(getString(R.string.sure)) { _, _ ->
                    mViewModel.outLogin()
                    ActivityHelper.start(LoginActivity::class.java)
                    ActivityHelper.finish(PswLockActivity::class.java)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                }
                .show()
        }

        //密码监听
        et_psw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length == 6) {
                    mViewModel.checkPsw(isSetPsw, p0.toString())
                }
            }
        })

        //密码校验状态
        mViewModel.checkStatus.observe(this, Observer {
            if (!it) {
                et_psw.text.clear()
                Utils.showToast("验证失败")
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isSetPsw) {
                val home = Intent(Intent.ACTION_MAIN)
                home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                home.addCategory(Intent.CATEGORY_HOME)
                startActivity(home)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (isSetPsw && mViewModel.checkStatus.value == null && !SessionUtils.isLock()) {
            Bus.post(SET_APP_LOCK, false)
        }
        super.onDestroy()
    }
}