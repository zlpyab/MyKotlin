package com.example.mykotlin.model.bean

data class Navigation(
    val cid: Int,
    val name: String,
    val articles: List<Article>
)