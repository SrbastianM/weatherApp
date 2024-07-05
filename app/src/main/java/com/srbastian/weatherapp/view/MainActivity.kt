package com.srbastian.weatherapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.savedstate.SavedStateRegistryOwner
import com.squareup.picasso.Picasso
import com.srbastian.weatherapp.R
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.databinding.ActivityMainBinding
import com.srbastian.weatherapp.network.RetrofitClient
import com.srbastian.weatherapp.viewmodel.MainViewModel
import com.srbastian.weatherapp.viewmodel.MainViewModelFactory
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private val apiKey = "fed18cf787e6b608067dcbb27c2a4257"
    private val city = "Guatavita"

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            WeatherRepository(RetrofitClient().retrofitApi)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        mainActivityBinding.btnStart.setOnClickListener {
            val intent = Intent(applicationContext, FirstPageActivity::class.java)
            intent.putExtra("city", city)
            intent.putExtra("apiKey", apiKey)
            startActivity(intent)
            finish()
        }
        mainViewModel.currentWeather.observe(this, Observer { weather ->
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.weather[0].icon}" + ".png")
                .resize(350, 350)
                .into(mainActivityBinding.ivWeather)
        })

        mainViewModel.fetchCurrentWeather(city, apiKey)
    }

}