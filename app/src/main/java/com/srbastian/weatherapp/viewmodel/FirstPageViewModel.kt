package com.srbastian.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.model.Card
import com.srbastian.weatherapp.model.current.CurrentWeatherModel
import com.srbastian.weatherapp.model.forecast.ForecastWeatherModel
import kotlinx.coroutines.launch

class FirstPageViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _currentWeather = MutableLiveData<CurrentWeatherModel>()
    private val _forecastWeather = MutableLiveData<ForecastWeatherModel>()

    private val _card = MutableLiveData<Card>()


    // weather: [WeatherModel] ->
    // WeatherModel : main : "", description: "", icon: "string"
    val currentWeather: MutableLiveData<CurrentWeatherModel> get() = _currentWeather
    val forecastWeather: MutableLiveData<ForecastWeatherModel> get() = _forecastWeather

    val setCard : MutableLiveData<Card> get() = _card

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

    fun setBigCard(card: Card) {
        _card.postValue(card)
    }
}