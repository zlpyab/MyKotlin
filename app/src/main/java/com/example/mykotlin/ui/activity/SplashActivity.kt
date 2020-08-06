package com.example.mykotlin.ui.activity

import android.Manifest
import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseActivity
import com.example.mykotlin.common.manager.GlideManager
import com.example.mykotlin.util.ActivityJumpUtils
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import com.example.mykotlin.viewModel.LoginViewModel
import com.gyf.immersionbar.BarHide
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    val duration: Long = 5

    private val mViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initView() {
        mImmersionBar!!.statusBarDarkFont(false).hideBar(BarHide.FLAG_HIDE_BAR).init()

        checkPermission()
    }

    private fun checkPermission() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (EasyPermissions.hasPermissions(this, permission)) {
            isNeedShowAdv()
        } else {
            EasyPermissions.requestPermissions(this, "该功能需要存储权限", 1000, permission)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setTitle("温馨提示")
                .setRationale("存储权限被拒绝，请去设置页面打开，以正常使用该功能")
                .setRequestCode(1002)
                .build().show()
        } else {
            Utils.showToast("权限被拒绝")
            finish()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        isNeedShowAdv()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1002) {
            checkPermission()
        }
    }

    private fun isNeedShowAdv() {
        mViewModel.downSplash()
        countTimer.start()
        tv_count.setBgColor(R.color.color_7f2d2d2d)
        tv_count.start(duration)
    }

    override fun setListener() {
        mViewModel.advertiseData.observe(this, Observer {
            tv_count.visibility = View.VISIBLE
            GlideManager.loadImg(it.imgUrl, iv_img, this)
        })

        tv_count.setOnClickListener {
            goLoginOrMain()
        }
    }

    private val countTimer: CountDownTimer = object : CountDownTimer((duration + 1) * 1000, 1000) {

        override fun onTick(time: Long) {
            var time = time / 1000
            if (time == 0L) {
                tv_count.isClickable = false
            }
            tv_count.text = "${time}跳过"
        }

        override fun onFinish() {
            goLoginOrMain()
        }
    }

    private fun goLoginOrMain() {
        tv_count.stop()
        if (TextUtils.isEmpty(SessionUtils.getLoginKey())) {
            ActivityJumpUtils.jumpToLoginActivity(this)
        } else {
            ActivityJumpUtils.jumpToMainActivity(this)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countTimer.let {
            countTimer.cancel()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
}