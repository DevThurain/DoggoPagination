package com.development.doggopagination.data.vo

import com.google.gson.annotations.SerializedName

data class DoggoVO(
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "url")
    val url: String
)
