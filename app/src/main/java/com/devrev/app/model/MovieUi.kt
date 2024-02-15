package com.devrev.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUi(
    val id: Int,
    val image: String,
    val title: String,
    val createdAt:Int,
) : Parcelable