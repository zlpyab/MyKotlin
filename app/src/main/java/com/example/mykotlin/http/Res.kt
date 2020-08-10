package com.example.mykotlin.http

data class Res<T>( val errorCode: Int, private val data: T, val errorMsg: String) {

    fun resData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}