package com.srbastian.weatherapp.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.srbastian.weatherapp.R
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.databinding.ActivityFirstPageBinding
import com.srbastian.weatherapp.network.RetrofitClient
import com.srbastian.weatherapp.viewmodel.MainViewModel
import com.srbastian.weatherapp.viewmodel.MainViewModelFactory
import java.time.LocalDate

class FirstPageActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var firstPageBinding: ActivityFirstPageBinding
    private var selectedLayout : LinearLayout? = null
    private var layoutsBackgroundColor: Int = Color.WHITE

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            WeatherRepository(RetrofitClient().retrofitApi)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstPageBinding = ActivityFirstPageBinding.inflate(layoutInflater)
        val view = firstPageBinding.root
        setContentView(view)
        val currentCity = intent.extras?.getString("city")
        val apiKey = intent.extras?.getString("apiKey")
        val threeDaysWeek = listOf(
            firstPageBinding.lyToday,
            firstPageBinding.lyTomorrow,
            firstPageBinding.lyAfter
        )

        layoutsBackgroundColor = ContextCompat.getColor(this, R.color.lila)

        threeDaysWeek.forEach { layout ->
            selectOneDay(this, layout)
        }

        currentWeather()
        foreCastWeather()

        mainViewModel.fetchCurrentWeather(currentCity.toString(), apiKey.toString())
        mainViewModel.fetchForcastWeather(currentCity.toString(), apiKey.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun currentWeather() {
        val today: LocalDate = LocalDate.now()
        mainViewModel.currentWeather.observe(this) { weather ->
            firstPageBinding.tvDateFooterToday.text = today.dayOfMonth.toString()
            firstPageBinding.tvFooterToday.text = weather.weather[0].main
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.weather[0].icon}" + ".png")
                .resize(50, 50).into(firstPageBinding.ivCardFooterToday)
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.weather[0].icon}" + ".png")
                .resize(350, 350)
                .into(firstPageBinding.ivCard)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun foreCastWeather() {
        val today: LocalDate = LocalDate.now()
        mainViewModel.forecastWeather.observe(this) { weather ->
            firstPageBinding.tvFooterTomorrow.text = weather.list[0].weather[0].main
            firstPageBinding.tvDateFooterTomorrow.text = today.plusDays(1).dayOfMonth.toString()
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.list[0].weather[0].icon}" + ".png")
                .resize(50, 50)
                .into(firstPageBinding.ivCardFooterTomorrow)
        }
    }

    private fun selectOneDay(context: Context, lay: LinearLayout) {
        lay.setOnClickListener {
            // If i was selected one, unselect
            selectedLayout?.let {
                animateChangeBackgroundColor(it, ContextCompat.getColor(context, R.color.lila))
            }
            // or select new one
            animateChangeBackgroundColor(lay, ContextCompat.getColor(context, R.color.light_lila))

            // update the state of the layout selected
            selectedLayout = lay
        }
    }
    private fun animateChangeBackgroundColor(lay : LinearLayout, colorTo: Int) {
        val colorFrom = lay.background
        if(colorFrom is ColorDrawable) {
            val colorFromInt = colorFrom.color
            val animator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromInt, colorTo)
            animator.duration = 300
            animator.interpolator = AccelerateInterpolator()
            animator.addUpdateListener {valueAnimator ->
                val animatedColor = valueAnimator.animatedValue as Int
                lay.setBackgroundColor(animatedColor)
            }
            animator.start()
        }
    }
}