package com.pethoalpar.androidtesstwoocr.response

import com.google.gson.annotations.SerializedName
import com.pethoalpar.androidtesstwoocr.model.SomsriAccount

data class AllQuickSaleResponse constructor(
        @SerializedName("username") val username: List<SomsriAccount>,
        @SerializedName("password") val password: List<SomsriAccount>
)