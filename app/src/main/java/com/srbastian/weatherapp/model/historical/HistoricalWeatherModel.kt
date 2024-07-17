package com.srbastian.weatherapp.model.historical

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class HistoricalWeatherModel(
    @JsonProperty("lat") val lat : Double,
    @JsonProperty("lon") val lon : Double,
    @JsonProperty("data")val data : List<HistoricalWeather>,
)