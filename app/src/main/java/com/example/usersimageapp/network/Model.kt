package com.example.usersimageapp.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Users(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
) : Parcelable

data class Albums(
val albumId : Int,
val title : String,
val url : String,
val thumbnailUrl : String
)