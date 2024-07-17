package com.srbastian.weatherapp.network

import com.srbastian.weatherapp.model.current.CurrentWeatherModel
import com.srbastian.weatherapp.model.forecast.ForecastWeatherModel
import com.srbastian.weatherapp.model.historical.HistoricalWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    // Provides the endpoints to set request for the API
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherModel

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastWeatherModel

    @GET("history")
    suspend fun getHistoricalWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("dt") dt: String,
        @Query("appid") apiKey: String = "",
        @Query("units") units: String = "metric"
    ): HistoricalWeatherModel // Change the model to Historical
}