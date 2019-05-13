package com.pethoalpar.androidtesstwoocr.services

import com.pethoalpar.androidtesstwoocr.MainApp
import javax.inject.Inject

open class Service {
    @Inject
    lateinit var apiService: APIService

    constructor() {
        MainApp.graph.inject(this)
    }

    fun getSomsriAccount() = apiService.getUser("test@test.com", "xx")

}