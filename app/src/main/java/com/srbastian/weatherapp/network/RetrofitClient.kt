package com.srbastian.weatherapp.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitClient {
    private val baseUrl = "https://api.openweathermap.org/data/2.5/"

    val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        JacksonConverterFactory.create(
            jacksonObjectMapper()
        )
    ).build()

    val retrofitApi: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
}