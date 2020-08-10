package com.example.mykotlin.http

import com.example.mykotlin.App
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by zlp on 2020/7/25 0025.
 * 网络管理类
 */
object HttpManager {

    /**cookie**/
    private val cookiePersistor = SharedPrefsCookiePersistor(App.instance)
    private val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API.base_url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val httpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cookieJar(cookieJar)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


    val apiService: API = retrofit.create(API::class.java)

    /**清除Cookie*/
    fun clearCookie() = cookieJar.clear()

    /**是否有Cookie*/
    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()
}