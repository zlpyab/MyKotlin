package com.example.mykotlin.ui.main.system

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/17 0017.
 */
class SystemRepository {
    suspend fun getSystemCategory() = HttpManager.apiService.getSystemCategory().resData()
}