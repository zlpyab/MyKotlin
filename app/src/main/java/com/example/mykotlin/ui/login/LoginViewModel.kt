package com.example.mykotlin.ui.login

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.BusUtils
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/7/27 0027.
 */
class LoginViewModel : BaseViewModel() {

    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    private val loginRepository by lazy { LoginRepository() }

    fun login(account: String, psw: String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = loginRepository.login(account,psw)
                SessionUtils.setUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED,true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )
    }
}