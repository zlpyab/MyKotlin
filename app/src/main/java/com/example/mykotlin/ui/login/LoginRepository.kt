package com.example.mykotlin.ui.login

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/12 0012.
 */
class LoginRepository {

    suspend fun login(account : String,psw : String) = HttpManager.apiService.login(account,psw).resData()

}