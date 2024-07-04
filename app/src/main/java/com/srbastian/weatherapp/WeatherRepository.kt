package com.srbastian.weatherapp

import com.srbastian.weatherapp.model.CurrentWeatherModel
import com.srbastian.weatherapp.network.RetrofitAPI
import com.srbastian.weatherapp.network.RetrofitClient

class WeatherRepository(private val retrofitClient: RetrofitAPI) {
    // Return the response of retrofit and set it in the model class
    suspend fun getCurrentWeather(city: String, apiKey: String): CurrentWeatherModel {
        return retrofitClient.getCurrentWeather(city, apiKey)
    }

    suspend fun getForecastWeather() {}
    suspend fun getHistoricalWeather() {}
}