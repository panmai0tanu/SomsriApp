package com.pethoalpar.androidtesstwoocr.services

import com.pethoalpar.androidtesstwoocr.model.SomsriAccount
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface APIService {
    @GET("/")
    fun getUser(@Query("username") username: String?, @Query("password") password: String? ): Observable<SomsriAccount>
}