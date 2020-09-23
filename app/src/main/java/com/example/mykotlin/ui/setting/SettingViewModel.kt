package com.example.mykotlin.ui.setting

import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.model.store.UserInfoStore

/**
 * Created by zlp on 2020/9/5 0005.
 */
class SettingViewModel : BaseViewModel() {

    fun outLogin(){
        UserInfoStore.clearUser()
        HttpManager.clearCookie()
        Bus.post(USER_LOGIN_STATE_CHANGED,false)
    }
}