package com.example.mykotlin.ui.register

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/12 0012.
 */
class RegisterRepository {

    suspend fun register(account : String,psw : String,confirmPsw : String) =
        HttpManager.apiService.register(account,psw,confirmPsw).resData()
}