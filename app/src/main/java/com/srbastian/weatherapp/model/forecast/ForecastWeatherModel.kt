package com.srbastian.weatherapp.model.forecast

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ForecastWeatherModel(
    @JsonProperty("list") val list: List<ForecastWeatherListModel>
)