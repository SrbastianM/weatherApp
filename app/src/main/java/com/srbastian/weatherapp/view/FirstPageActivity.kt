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
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
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
        val search = firstPageBinding.search
        layoutsBackgroundColor = ContextCompat.getColor(this, R.color.lila)

        threeDaysWeek.forEach { layout ->
            selectOneCard(this, layout, threeDaysWeek)
        }

        searchViewText()
        afterTomorrowSmallCardWeather()
        todaySmallCardWeather()
        forecastSmallCardWeather()

        updateBigCard()
        firstPageViewModel.fetchCurrentWeather(currentCity.toString(), apiKey.toString())
        firstPageViewModel.fetchForcastWeather(currentCity.toString(), apiKey.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun todaySmallCardWeather() {
        val today: LocalDate = LocalDate.now()
        firstPageViewModel.currentWeather.observe(this) { smallCard ->
            Log.d("Test", smallCard.weather.toString())
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
            Log.d("Test", weather.list.toString())
            firstPageBinding.tvFooterTomorrow.text = weather.list[1].weather[0].main
            firstPageBinding.tvDateFooterTomorrow.text = today.plusDays(1).dayOfMonth.toString()
            Picasso.get()
                .load("http://openweathermap.org/img/w/${weather.list[1].weather[0].icon}" + ".png")
                .resize(50, 50)
                .into(firstPageBinding.ivCardFooterTomorrow)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun afterTomorrowSmallCardWeather() {
        val today: LocalDate = LocalDate.now().plusDays(2)
        firstPageViewModel.forecastWeather.observe(this) { afterTomorrow ->
            val title = today.dayOfMonth.toString()
            firstPageBinding.tvFooterAfterTomorrow.text = afterTomorrow.list[10].weather[0].main
            firstPageBinding.tvDateFooterAfterTomorrow.text = title
            val icon = Picasso.get()
                .load("http://openweathermap.org/img/w/${afterTomorrow.list[10].weather[0].icon}" + ".png")
                .resize(50, 50)
                .into(firstPageBinding.ivCardFooterAfterTomorrow)
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
            val card = Card(title, icon, description)
            firstPageViewModel.setBigCard(card)
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

            Card(title, icon, description)
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
                layouts[2] -> updateAfterBigCard()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAfterBigCard() {
        val today: LocalDate = LocalDate.now().plusDays(2)
        val dateFormater = DateTimeFormatter.ofPattern("EEEE, d MMM, yyyy")
        val formattedDate = today.format(dateFormater)
        firstPageViewModel.forecastWeather.observe(this) { afterTomorrow ->
            val title = formattedDate
            val description = afterTomorrow.list[10].weather[0].main
            firstPageBinding.cardDescription.text = description
            firstPageBinding.cardTitle.text = title
            val icon = Picasso.get()
                .load("http://openweathermap.org/img/w/${afterTomorrow.list[10].weather[0].icon}" + ".png")
                .resize(50, 50)
                .into(firstPageBinding.ivCard)
            Card(title, icon, description)
            firstPageViewModel.setCard
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun searchViewText() {
        val searchText =
            firstPageBinding.search
        searchText.queryHint = intent.extras?.getString("city")
    }
}