package com.example.mykotlin.ui.main.home.square

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/14 0014.
 */
class SquareRepository {

    suspend fun getSquareListData(page : Int) = HttpManager.apiService.getSquareList(page).resData()
}