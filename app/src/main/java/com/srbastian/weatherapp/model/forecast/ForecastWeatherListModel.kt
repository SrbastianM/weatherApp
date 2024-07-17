package com.srbastian.weatherapp.model.forecast

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.srbastian.weatherapp.model.main.MainModel
import com.srbastian.weatherapp.model.weather.WeatherModel

@JsonIgnoreProperties(ignoreUnknown = true)
data class ForecastWeatherListModel(
    @JsonProperty("dt") val dt: Double,
    @JsonProperty("main") val main: MainModel,
    @JsonProperty("weather") val weather: List<WeatherModel>,
)