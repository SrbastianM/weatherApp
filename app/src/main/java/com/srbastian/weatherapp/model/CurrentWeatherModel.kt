package com.srbastian.weatherapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrentWeatherModel(
    val weather: List<WeatherModel>
)