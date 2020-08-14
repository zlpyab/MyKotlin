package com.example.mykotlin.ui.common

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/14 0014.
 */
class CollectRepository {

    //收藏
    suspend fun collect(id :Int){ HttpManager.apiService.collect(id).resData()}
    //取消收藏
    suspend fun unCollect(id: Int){HttpManager.apiService.unCollect(id).resData()}

}