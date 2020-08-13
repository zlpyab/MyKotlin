package com.example.mykotlin.ui.register

import android.util.MutableDouble
import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/8/10 0010.
 */
class RegisterViewModel : BaseViewModel() {

    val submitting = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    private val registerRepository by lazy { RegisterRepository() }

    fun register(account: String, psw: String, confirmPsw: String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = registerRepository.register(account,psw,confirmPsw)
                SessionUtils.setUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                registerResult.value = true
            },
            error = {
                submitting.value = false
                registerResult.value = false
            }
        )
    }
}