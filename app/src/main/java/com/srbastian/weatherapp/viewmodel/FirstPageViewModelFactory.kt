package com.srbastian.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srbastian.weatherapp.WeatherRepository
import java.lang.IllegalArgumentException

class FirstPageViewModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FirstPageViewModel::class.java)) {
            FirstPageViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unkown ViewModel class")
        }
    }
}