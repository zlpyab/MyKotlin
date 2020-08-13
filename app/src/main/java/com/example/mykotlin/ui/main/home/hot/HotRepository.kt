package com.example.mykotlin.ui.main.home.hot

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/12 0012.
 */
class HotRepository  {

    //获取顶置数据
    suspend fun getTopArticleList() = HttpManager.apiService.getTopArticleList().resData()
    //获取文章数据
    suspend fun getArticleList(page:Int) = HttpManager.apiService.getArticleList(page).resData()
}