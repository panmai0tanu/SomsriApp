package com.pethoalpar.androidtesstwoocr.services

import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.model.Item
import javax.inject.Inject

open class Service {
    @Inject
    lateinit var apiService: APIService

    constructor() {
        MainApp.graph.inject(this)
    }

    fun getSomsriAccount(username: String, password: String) = apiService.getUser(username, password)
    fun sendData(userId: Int, id: Int, detail: String, cost: Double, date: String) = apiService.sendData(userId, id, detail, cost, date)

}