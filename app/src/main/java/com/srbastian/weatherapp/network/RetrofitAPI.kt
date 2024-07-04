package com.srbastian.weatherapp.network

import com.srbastian.weatherapp.model.CurrentWeatherModel
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
    ): CurrentWeatherModel // Check models that do not work -> WeatherModel and WeatherDataModel

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): CurrentWeatherModel //Change the model to forecast

    @GET("onecall/timemachine")
    fun getHistoricalWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("dt") dt: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<List<CurrentWeatherModel>> // Change the model to Historical
}