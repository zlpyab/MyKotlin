package com.example.mykotlin.ui.main.home.wechat

import com.example.mykotlin.http.HttpManager

/**
 * Created by zlp on 2020/8/17 0017.
 */
class WechatRepository  {

    suspend fun getWechatCategories() = HttpManager.apiService.getWechatCategories().resData()
    suspend fun getWechatArticelList(page : Int,cid : Int) = HttpManager.apiService.getWechatArticleList(page,cid).resData()
}