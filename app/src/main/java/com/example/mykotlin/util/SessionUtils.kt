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
        SPUtils.getInstance().put(Constants.key_user_info, mGson.toJson(userInfo))
    }

    fun getUserInfo(): UserInfo? {
        var userJson = SPUtils.getInstance().getString(Constants.key_user_info, "")
        return if (userJson.isNotEmpty()) {
            mGson.fromJson(userJson, UserInfo::class.java)
        } else {
            null
        }
    }

    fun addCollectId(id: Int){
        getUserInfo()?.let {
            if (id !in  it.collectIds){
                it.collectIds.add(id)
                setUserInfo(it)
            }
        }
    }

    fun removeCollectId(id: Int){
         getUserInfo()?.let {
             if (id in  it.collectIds){
                 it.collectIds.remove(id)
                 setUserInfo(it)
             }
         }
    }


    fun clearUserInfo() {
        setUserInfo(null)
    }
}