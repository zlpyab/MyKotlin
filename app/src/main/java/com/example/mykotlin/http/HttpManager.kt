package com.example.mykotlin.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by zlp on 2020/7/25 0025.
 * 网络管理类
 */
class HttpManager {

    companion object {
        val instance: HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpManager()
        }
    }

    val service by lazy {
         getService(API::class.java, API.base_url)
    }

    private fun <T> getService(serviceClass: Class<T>, baseUrl: String): T {
        val json = GsonBuilder().setLenient().registerTypeAdapterFactory(StringAdapterFactory()).create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(json))
            .build()
            .create(serviceClass)
    }

    private val httpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}