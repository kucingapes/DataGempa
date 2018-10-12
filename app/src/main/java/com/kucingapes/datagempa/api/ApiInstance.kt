/*
 * ApiInstance.kt on DataGempa
 * Developed by Muhammad Utsman on 10/12/18 4:54 PM
 * Last modified 10/12/18 3:54 PM
 * Copyright (c) 2018 kucingapes
 */

package com.kucingapes.datagempa.api

import com.kucingapes.datagempa.model.DataGempa
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface ApiInstance {

    @GET("/gempadirasakan.xml")
    fun getData(): Call<DataGempa.Infogempa>

    companion object {
        fun create(): ApiInstance {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl("http://data.bmkg.go.id")
                    .build()
            return retrofit.create(ApiInstance::class.java)
        }
    }
}