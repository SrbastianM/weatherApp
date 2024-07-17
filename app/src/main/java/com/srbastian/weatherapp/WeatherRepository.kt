package com.srbastian.weatherapp

import com.srbastian.weatherapp.model.current.CurrentWeatherModel
import com.srbastian.weatherapp.model.forecast.ForecastWeatherModel
import com.srbastian.weatherapp.model.historical.HistoricalWeatherModel
import com.srbastian.weatherapp.network.RetrofitAPI
import java.time.Instant
import java.time.temporal.ChronoUnit

class WeatherRepository(private val retrofitClient: RetrofitAPI) {
    // Return the response of retrofit and set it in the model class
    suspend fun getCurrentWeather(city: String, apiKey: String): CurrentWeatherModel {
        return retrofitClient.getCurrentWeather(city, apiKey)
    }

    suspend fun getForecastWeather(city: String, apiKey: String): ForecastWeatherModel {
        return retrofitClient.getForecastWeather(city, apiKey)
    }
    suspend fun getHistoricalWeather(lat: Double, lon : Double, apiKey: String): HistoricalWeatherModel {
        return retrofitClient.getHistoricalWeather(lat, lon, apiKey)
    }

    suspend fun getForecastWeatherAfterTomorrow(city: String, apiKey: String): ForecastWeatherModel {
        val forecast = getForecastWeather(city, apiKey)
        return forecast.list.first { it.dt > Instant.now().plus(2, ChronoUnit.DAYS).epochSecond}
    }

}