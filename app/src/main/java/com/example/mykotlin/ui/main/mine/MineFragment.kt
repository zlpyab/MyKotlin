package com.example.mykotlin.ui.main.mine

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseFragment
import com.example.mykotlin.base.BaseVmFragment
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.setting.SettingActivity
import com.example.mykotlin.util.ActivityHelper
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by zlp on 2020/8/11 0011.
 */
class MineFragment : BaseVmFragment<MineViewModel>() {

    companion object{
        fun newInstance() = MineFragment()
    }

    override fun initLayout() = R.layout.fragment_mine

    override fun viewModelClass() = MineViewModel::class.java

    override fun initView() {

        rl_setting.setOnClickListener {
            ActivityHelper.start(SettingActivity::class.java)
        }

        rl_user.setOnClickListener {
            checkLogin{}
        }

        changeLoginUi()
    }

    override fun observe() {
        super.observe()
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner, Observer {
            changeLoginUi()
        })
    }

    private fun changeLoginUi() {
         var isLogin = isLogin()
         var userInfo = UserInfoStore.getUserInfo()
         tv_name.isVisible = isLogin
         tv_id.isVisible = isLogin
         tv_login.isVisible = !isLogin
         if (isLogin && userInfo != null){
             tv_name.text = userInfo.nickname
             tv_id.text = "ID:${userInfo.id}"
         }
    }
}