package com.srbastian.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.model.CurrentWeatherModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _currentWeather = MutableLiveData<CurrentWeatherModel>()
        // weather: [WeatherModel] ->
        // WeatherModel : main : "", description: "", icon: "string"
    val currentWeather: MutableLiveData<CurrentWeatherModel> get() = _currentWeather


    fun fetchCurrentWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = repository.getCurrentWeather(city, apiKey)
            _currentWeather.postValue(result)
        }
    }
}