package com.srbastian.weatherapp.model.main

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MainModel(
    @JsonProperty("temp") val temp: Double,
    @JsonProperty("humidity") val humidity: Int,
)