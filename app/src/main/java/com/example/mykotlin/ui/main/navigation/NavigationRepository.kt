package com.example.mykotlin.ui.main.navigation

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/10/12 0012.
 */
class NavigationRepository {

   suspend fun getNavigation() = HttpManager.apiService.getNavigations().resData()

}