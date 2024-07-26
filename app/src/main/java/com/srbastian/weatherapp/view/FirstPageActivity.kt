package com.srbastian.weatherapp.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.squareup.picasso.Picasso
import com.srbastian.weatherapp.R
import com.srbastian.weatherapp.WeatherRepository
import com.srbastian.weatherapp.databinding.ActivityFirstPageBinding
import com.srbastian.weatherapp.model.Card
import com.srbastian.weatherapp.network.RetrofitClient
import com.srbastian.weatherapp.viewmodel.FirstPageViewModel
import com.srbastian.weatherapp.viewmodel.FirstPageViewModelFactory
import com.srbastian.weatherapp.viewmodel.MainViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FirstPageActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var firstPageBinding: ActivityFirstPageBinding
    private var selectedLayout: LinearLayout? = null
    private var layoutsBackgroundColor: Int = Color.WHITE

    private val firstPageViewModel: FirstPageViewModel by viewModels {
        FirstPageViewModelFactory(
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
            selectOneCard(this, layout, threeDaysWeek)
        }
        todaySmallCardWeather()
        forecastSmallCardWeather()

        firstPageViewModel.fetchCurrentWeather(currentCity.toString(), apiKey.toString())
        firstPageViewModel.fetchForcastWeather(currentCity.toString(), apiKey.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun todaySmallCardWeather() {
        val today: LocalDate = LocalDate.now()
        firstPageViewModel.currentWeather.observe(this) { smallCard ->
            firstPageBinding.tvDateFooterToday.text = today.dayOfMonth.toString()
            firstPageBinding.tvFooterToday.text = smallCard.weather[0].main
            Picasso.get()
                .load("http://openweathermap.org/img/w/${smallCard.weather[0].icon}" + ".png")
                .resize(50, 50).into(firstPageBinding.ivCardFooterToday)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun forecastSmallCardWeather() {
        val today: LocalDate = LocalDate.now()
        firstPageViewModel.forecastWeather.observe(this) { weather ->
            firstPageBinding.tvFooterTomorrow.text = weather.list[0].weather[0].main
            firstPageBinding.tvDateFooterTomorrow.text = today.plusDays(1).dayOfMonth.toString()
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.list[0].weather[0].icon}" + ".png")
                .resize(50, 50)
                .into(firstPageBinding.ivCardFooterTomorrow)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateForecastBigCard() {
        val today: LocalDate = LocalDate.now().plusDays(1)
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM, yyyy")
        val formattedDate = today.format(dateFormatter)
        today.plusDays(1)
        firstPageViewModel.forecastWeather.observe(this) { bigCard ->
            val description = bigCard.list[0].weather[0].main
            firstPageBinding.cardDescription.text = description
            val title = formattedDate
            firstPageBinding.cardTitle.text = title
            val icon = Picasso.get()
                .load("http://openweathermap.org/img/w/${bigCard.list[0].weather[0].icon}" + ".png")
                .resize(118, 124)
                .into(firstPageBinding.ivCard)
            Card(title, icon, description)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateBigCard() {
        val today: LocalDate = LocalDate.now()
        val dateFormater = DateTimeFormatter.ofPattern("EEEE, d MMM, yyyy")
        val formattedDate = today.format(dateFormater)
        firstPageViewModel.currentWeather.observe(this) { bigCard ->
            val description = bigCard.weather[0].description
            firstPageBinding.cardDescription.text = description
            val title = formattedDate.toString()
            firstPageBinding.cardTitle.text = title
            val icon = Picasso.get()
                .load("http://openweathermap.org/img/w/${bigCard.weather[0].icon}" + ".png")
                .resize(118, 124)
                .into(firstPageBinding.ivCard)

            Card(title, icon, description) // Esta parte creo que esta mal por que no hace nada
            firstPageViewModel.setCard
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectOneCard(context: Context, lay: LinearLayout, layouts: List<LinearLayout>) {
        lay.setOnClickListener {
            // If i was selected one, unselect
            selectedLayout?.let {
                animateChangeBackgroundColor(it, ContextCompat.getColor(context, R.color.lila))
            }
            // or select new one
            animateChangeBackgroundColor(lay, ContextCompat.getColor(context, R.color.light_lila))

            // update the state of the layout selected
            selectedLayout = lay

            when (lay) {
                layouts[0] -> updateBigCard()
                layouts[1] -> updateForecastBigCard()
            }

        }
    }

    private fun animateChangeBackgroundColor(lay: LinearLayout, colorTo: Int) {
        val colorFrom = lay.background
        if (colorFrom is ColorDrawable) {
            val colorFromInt = colorFrom.color
            val animator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromInt, colorTo)
            animator.duration = 300
            animator.interpolator = AccelerateInterpolator()
            animator.addUpdateListener { valueAnimator ->
                val animatedColor = valueAnimator.animatedValue as Int
                lay.setBackgroundColor(animatedColor)
            }
            animator.start()
        }
    }
}