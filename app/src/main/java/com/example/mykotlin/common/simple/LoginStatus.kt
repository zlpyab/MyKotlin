package com.example.mykotlin.common.simple

import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.model.bean.UserInfo
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/8/10 0010.
 */
fun isLogin() = SessionUtils.getUserInfo() != null && HttpManager.hasCookie()