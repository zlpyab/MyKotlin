package com.example.mykotlin.ui.main.system.pager

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/10/13 0013.
 */
class SystemPagerRepository  {

     suspend fun getArticleListByCid(page:Int,cid : Int) = HttpManager.apiService.getArticleListByCid(page,cid).resData()

}