package com.pethoalpar.androidtesstwoocr.model

import com.google.gson.annotations.SerializedName


data class SomsriAccount(
        @SerializedName("user") val user: User?,
        @SerializedName("password") val password: String?,
        @SerializedName("id") val id: Int,
        @SerializedName("detail") val detail: String,
        @SerializedName("cost") val cost: Double,
        @SerializedName("date") val date: String

)