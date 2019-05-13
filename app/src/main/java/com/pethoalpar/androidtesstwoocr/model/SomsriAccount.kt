package com.pethoalpar.androidtesstwoocr.model

import com.google.gson.annotations.SerializedName


data class SomsriAccount(
        @SerializedName("user") val user: List<User>?,
        @SerializedName("password") val password: String?
)