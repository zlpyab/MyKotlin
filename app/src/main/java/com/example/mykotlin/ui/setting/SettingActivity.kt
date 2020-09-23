package com.example.mykotlin.ui.setting

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseVmActivity
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.SET_APP_LOCK
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.psw.PswLockActivity
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import com.example.mykotlin.util.setNightMode
import kotlinx.android.synthetic.main.activity_details.titleView
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseVmActivity<SettingViewModel>() {


    override fun getLayoutId() = R.layout.activity_setting

    override fun viewModelClass() = SettingViewModel::class.java


    var isSetPsw = false
    override fun initView() {
        titleView.bind(SettingActivity::class.java, getString(R.string.system_setting))

        view_switch.isChecked = SessionUtils.getNightMode()
        switch_lock.isChecked = SessionUtils.isLock()
        tv_out_login.isVisible = isLogin()

        view_switch.setOnCheckedChangeListener { _, checked ->
            setNightMode(checked)
            SessionUtils.setNightMode(checked)
        }

        switch_lock.setOnClickListener {
            checkLogin {
                isSetPsw = !SessionUtils.isLock()
                Constants.isCheckPsw = isSetPsw
                ActivityHelper.start(PswLockActivity::class.java, mapOf(Constants.key_data to isSetPsw))
            }
        }

        tv_out_login.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.sure_out_login))
                .setPositiveButton(getString(R.string.sure)) { _, _ ->
                    mViewModel.outLogin()
                    ActivityHelper.start(LoginActivity::class.java)
                    ActivityHelper.finish(SettingActivity::class.java)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                }
                .show()
        }
    }

    override fun observe() {
        super.observe()
        Bus.observe<Boolean>(SET_APP_LOCK, this, Observer {
            switch_lock.isChecked = it
            if (it) {
                Utils.showToast("设置成功")
            } else {
                if (!isSetPsw) {
                    Utils.showToast("已取消密码锁")
                }
            }
        })
    }
}