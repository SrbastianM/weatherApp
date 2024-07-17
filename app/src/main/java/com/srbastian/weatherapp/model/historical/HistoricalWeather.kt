package com.srbastian.weatherapp.model.historical

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.srbastian.weatherapp.model.weather.WeatherModel

@JsonIgnoreProperties(ignoreUnknown = true)
data class HistoricalWeather (
        @JsonProperty("dt") val dt : Int,
        @JsonProperty("temp") val temp : Double,
        @JsonProperty("weather") val weather : List<WeatherModel>,
        )