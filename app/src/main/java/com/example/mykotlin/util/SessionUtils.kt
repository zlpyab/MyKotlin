package com.example.mykotlin.util

import com.blankj.utilcode.util.SPUtils
import com.example.mykotlin.common.Constants

/**
 * Created by zlp on 2020/7/28 0028.
 */
object SessionUtils {

    //存loginKey
    fun setLoginKey(loginKey : String){
        SPUtils.getInstance().put(Constants.key_loginKey,loginKey)
    }

    //获取loginKey
    fun getLoginKey() : String{
        return SPUtils.getInstance().getString(Constants.key_loginKey)
    }

    //存cellPhone
    fun setCellPhone(cellPhone : String){
        SPUtils.getInstance().put(Constants.key_phone,cellPhone)
    }

    //获取cellPhone
    fun getCellPhone():String{
        return SPUtils.getInstance().getString(Constants.key_phone)
    }

}