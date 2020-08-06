package com.example.mykotlin.http

import com.example.mykotlin.http.Res
import com.example.mykotlin.model.bean.AdvertiseBean
import com.example.mykotlin.model.bean.LoginBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by zlp on 2020/7/25 0025.
 */
interface API {

    companion object {
        var base_url = "https://app.hyy10086.com/"
    }

    @FormUrlEncoded
    @POST("app/login.do")
    suspend fun login(
        @Field("cellphone") cellPhone: String = "",
        @Field("password") password: String = "",
        @Field("type") type: Int = 0
    ): Res<LoginBean>

    @POST("/app/getAdImages.do")
    suspend fun getAdvertise(): Res<AdvertiseBean>
}