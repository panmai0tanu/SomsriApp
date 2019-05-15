package com.pethoalpar.androidtesstwoocr.services

import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.SomsriAccount
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

interface APIService {
    @GET("/user_api")
    fun getUser(@Query("username") username: String?, @Query("password") password: String? ): Observable<SomsriAccount>

    @GET("/add_item")
    fun sendData(@Query("userId") username: Int?, @Query("id") id: Int, @Query("detail") detail:String, @Query("cost") cost:  Double, @Query("date") date: String): Observable<SomsriAccount>
}