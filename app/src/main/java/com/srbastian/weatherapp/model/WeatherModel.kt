package com.srbastian.weatherapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherModel(val id: Int, val main: String, val description: String, val icon: String)