package com.example.mykotlin.http

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zlp on 2020/7/25 0025.
 */
class LogInterceptor :Interceptor {
    val tag = "YiLog"
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        var response = chain.proceed(request)

        //response.peekBody不会关闭流
        LogUtils.dTag(tag,format.format(Date()) + " Response " + "\nsuccessful:" + response.isSuccessful + "\nbody:" + response.peekBody(1024)?.string())

        return response
    }
}