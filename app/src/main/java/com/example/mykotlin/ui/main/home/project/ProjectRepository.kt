package com.example.mykotlin.ui.main.home.project

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/17 0017.
 */
class ProjectRepository {

    suspend fun getCategoryList() = HttpManager.apiService.getCategoryList().resData()
    suspend fun getProjectList(page : Int,cid : Int) = HttpManager.apiService.getProjectList(page,cid).resData()
}