package com.example.mykotlin.http

import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.Category
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

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page:Int) :Res<Pagination<Article>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id:Int) : Res<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int) : Res<Any?>

    @GET("article/listproject/{page}/json")
    suspend fun getLatestList(@Path("page") page: Int) : Res<Pagination<Article>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareList(@Path("page") page: Int) : Res<Pagination<Article>>

    @GET("project/tree/json")
    suspend fun getCategoryList() : Res<MutableList<Category>>

    @GET("project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid : Int
    ) : Res<Pagination<Article>>

    @GET("wxarticle/chapters/json")
    suspend fun getWechatCategories(): Res<MutableList<Category>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWechatArticleList(
        @Path("page") page: Int,
        @Path("id") id: Int
    ): Res<Pagination<Article>>
}