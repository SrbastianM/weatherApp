package com.srbastian.weatherapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.srbastian.weatherapp.model.main.MainModel
import com.srbastian.weatherapp.model.weather.WeatherModel
import com.srbastian.weatherapp.model.wind.WindModel

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrentWeatherModel(
    @JsonProperty("weather") val weather: List<WeatherModel>,
    @JsonProperty("main") val main: MainModel,
    @JsonProperty("wind") val wind: WindModel,
)