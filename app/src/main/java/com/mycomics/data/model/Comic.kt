package com.mycomics.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("desc")
    @Expose
    val desc: String,

    @SerializedName("image")
    @Expose
    val image: String

):Parcelable