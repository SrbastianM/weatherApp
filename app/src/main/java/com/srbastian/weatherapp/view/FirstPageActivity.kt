package com.srbastian.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.srbastian.weatherapp.R
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.databinding.ActivityFirstPageBinding
import com.srbastian.weatherapp.network.RetrofitClient
import com.srbastian.weatherapp.viewmodel.MainViewModel
import com.srbastian.weatherapp.viewmodel.MainViewModelFactory

class FirstPageActivity : AppCompatActivity() {
    lateinit var firstPageBinding: ActivityFirstPageBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            WeatherRepository(RetrofitClient().retrofitApi)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstPageBinding = ActivityFirstPageBinding.inflate(layoutInflater)
        val view = firstPageBinding.root
        setContentView(view)
        val currentCity = intent.extras?.getString("city")
        val apiKey = intent.extras?.getString("apiKey")

        mainViewModel.currentWeather.observe(this, { weather ->
            Log.d("Pruebas", weather.toString())
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.weather[0].icon}" + ".png")
                .resize(350, 350)
                .into(firstPageBinding.ivCard)
        })

        mainViewModel.fetchCurrentWeather(currentCity.toString(), apiKey.toString())
    }
}