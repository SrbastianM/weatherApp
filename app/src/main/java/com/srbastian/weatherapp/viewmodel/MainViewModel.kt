package com.srbastian.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.model.current.CurrentWeatherModel
import com.srbastian.weatherapp.model.forecast.ForecastWeatherModel
import com.srbastian.weatherapp.model.historical.HistoricalWeatherModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _historicalWeather = MutableLiveData<HistoricalWeatherModel>()
    private val _currentWeather = MutableLiveData<CurrentWeatherModel>()
    private val _forecastWeather = MutableLiveData<ForecastWeatherModel>()


    // weather: [WeatherModel] ->
    // WeatherModel : main : "", description: "", icon: "string"
    val currentWeather: MutableLiveData<CurrentWeatherModel> get() = _currentWeather
    val forecastWeather: MutableLiveData<ForecastWeatherModel> get() = _forecastWeather
    val historicalWeather : MutableLiveData<HistoricalWeatherModel> get() = _historicalWeather

    fun fetchCurrentWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = repository.getCurrentWeather(city, apiKey)
            _currentWeather.postValue(result)
        }
    }

    fun fetchForcastWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = repository.getForecastWeather(city, apiKey)
            _forecastWeather.postValue(result)
        }
    }

    fun fetchWeatherAfterTomorrow(city: String, apiKey: String) {

    }

    fun fetchHistoricalWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            val result = repository.getHistoricalWeather(lat, lon, apiKey)
            _historicalWeather.postValue(result)
        }
    }

}