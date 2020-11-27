package com.example.mykotlin.ui.main.discovery

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/10/13 0013.
 */
class DiscoveryRepository {

    suspend fun getBanners() = HttpManager.apiService.getBanner().resData()

    suspend fun getHotWords() = HttpManager.apiService.getHotWords().resData()

    suspend fun getWebkit() = HttpManager.apiService.getWebkit().resData()
}