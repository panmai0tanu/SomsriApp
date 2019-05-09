package com.pethoalpar.androidtesstwoocr.model

import com.google.gson.annotations.SerializedName


data class SomsriAccount(
        @SerializedName("username") val username: String?,
        @SerializedName("password") val password: String?
)