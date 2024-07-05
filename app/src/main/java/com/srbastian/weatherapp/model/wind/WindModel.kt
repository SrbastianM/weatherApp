package com.srbastian.weatherapp.model.wind

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WindModel(
    @JsonProperty("speed") val speed: Double
)