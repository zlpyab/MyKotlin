package com.example.mykotlin.ui.psw

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.Constants
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.SET_APP_LOCK
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.util.ActivityHelper
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/9/5 0005.
 */
class PswLockViewModel : BaseViewModel() {


    var checkStatus = MutableLiveData<Boolean>()

    fun checkPsw(isSetPsw: Boolean,psw: String) {
        if (isSetPsw) {
            SessionUtils.setLock(true)
            SessionUtils.setLockPsw(psw)
            Bus.post(SET_APP_LOCK, true)
            ActivityHelper.finish(PswLockActivity::class.java)
        } else {
            if (psw == SessionUtils.getLockPsw()) {
                if (!Constants.isCheckPsw){//不是验证
                    SessionUtils.setLockPsw("")
                    SessionUtils.setLock(false)
                    Bus.post(SET_APP_LOCK, false)
                }
                ActivityHelper.finish(PswLockActivity::class.java)
            } else {
                checkStatus.value = false
            }
        }
    }

    fun outLogin(){
        UserInfoStore.clearUser()
        HttpManager.clearCookie()
        SessionUtils.setLock(false)
        SessionUtils.setLockPsw("")
        Bus.post(SET_APP_LOCK,false)
        Bus.post(USER_LOGIN_STATE_CHANGED,false)
    }
}