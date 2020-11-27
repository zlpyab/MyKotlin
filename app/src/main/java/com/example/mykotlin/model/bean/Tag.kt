package com.example.mykotlin.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    var id: Long = 0,
    var articleId: Long = 0,
    var name: String = "",
    var url: String = ""
) : Parcelable