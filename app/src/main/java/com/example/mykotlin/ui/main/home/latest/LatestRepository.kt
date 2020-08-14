package com.example.mykotlin.ui.main.home.latest

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/14 0014.
 */
class LatestRepository {

    suspend fun getLatestListData(page: Int) =  HttpManager.apiService.getLatestList(page).resData()
}