package com.example.mykotlin.util

import android.text.TextUtils
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by zlp on 2020/7/27 0027.
 */
object CommonUtils {

        //MD5加密
        fun getMD5(str: String): String {
            if (TextUtils.isEmpty(str)) {
                return ""
            }
            var md5: MessageDigest? = null
            try {
                md5 = MessageDigest.getInstance("MD5")
                val bytes = md5.digest(str.toByteArray())
                var result = ""
                for (b in bytes) {
                    var temp = Integer.toHexString(b and 0xff)
                    if (temp.length == 1) {
                        temp = "0$temp"
                    }
                    result += temp
                }
                return result
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
    }
}