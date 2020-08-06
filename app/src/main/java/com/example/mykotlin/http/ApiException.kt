package com.example.mykotlin.http

class ApiException(var code: Int, override var message: String) : RuntimeException()