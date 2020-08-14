package com.example.mykotlin.model.store

import com.example.mykotlin.model.bean.UserInfo
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/8/14 0014.
 */
object UserInfoStore {

    fun getUserInfo() : UserInfo?{
        return SessionUtils.getUserInfo()
    }

    fun setUserInfo(info : UserInfo){
        SessionUtils.setUserInfo(info)
    }


    /**
     *添加收藏文章id
     */
    fun addCollectId(id : Int){
        getUserInfo()?.let {
            if (id !in  it.collectIds){
                it.collectIds.add(id)
                setUserInfo(it)
            }
        }
    }

    /**
     * 移除收藏文章id
     */
    fun removeCollectId(id: Int){
        getUserInfo()?.let {
            if (id in  it.collectIds){
                it.collectIds.remove(id)
                setUserInfo(it)
            }
        }
    }

}