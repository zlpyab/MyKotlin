package com.example.mykotlin.http

import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.Pagination
import com.example.mykotlin.model.bean.UserInfo
import retrofit2.http.*

/**
 * Created by zlp on 2020/7/25 0025.
 */
interface API {

    companion object {
        var base_url = "https://www.wanandroid.com/"
    }

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Res<UserInfo>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ):Res<UserInfo>


    @GET("article/top/json")
    suspend fun getTopArticleList():Res<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page:Int) :Res<Pagination<Article>>
}