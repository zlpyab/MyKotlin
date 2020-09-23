package com.example.mykotlin.util

import com.blankj.utilcode.util.SPUtils
import com.example.mykotlin.common.Constants
import com.example.mykotlin.model.bean.UserInfo
import com.google.gson.Gson


/**
 * Created by zlp on 2020/7/28 0028.
 */
object SessionUtils {

    private val mGson by lazy { Gson() }

    fun setUserInfo(userInfo: UserInfo?) {
        SPUtils.getInstance().put(Constants.key_user_info, if (null == userInfo) "" else mGson.toJson(userInfo))
    }

    fun getUserInfo(): UserInfo? {
        var userJson = SPUtils.getInstance().getString(Constants.key_user_info, "")
        return if (userJson.isNotEmpty()) {
            mGson.fromJson(userJson, UserInfo::class.java)
        } else {
            null
        }
    }

    fun setNightMode(isNight : Boolean){
        SPUtils.getInstance().put(Constants.key_night_mode,isNight)
    }

    fun getNightMode() : Boolean{
        return SPUtils.getInstance().getBoolean(Constants.key_night_mode)
    }

    fun setLock(isLock : Boolean){
        SPUtils.getInstance().put(Constants.key_lock,isLock)
    }

    fun isLock() : Boolean{
      return  SPUtils.getInstance().getBoolean(Constants.key_lock)
    }

    fun setLockPsw(psw :String){
        SPUtils.getInstance().put(Constants.key_lock_psw,psw)
    }

    fun getLockPsw():String{
        return SPUtils.getInstance().getString(Constants.key_lock_psw,"")
    }

    fun clearUserInfo() {
        setUserInfo(null)
    }
}