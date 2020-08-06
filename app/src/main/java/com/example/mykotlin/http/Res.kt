package com.example.mykotlin.http

data class Res<T>(
    val errcode: Int = 1,
    val data: T? = null,
    val info: String = ""
)