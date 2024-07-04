package com.srbastian.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srbastian.weatherapp.R
import com.srbastian.weatherapp.databinding.ActivityFirstPageBinding

class FirstPageActivity : AppCompatActivity() {
    lateinit var firstPageBinding: ActivityFirstPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstPageBinding = ActivityFirstPageBinding.inflate(layoutInflater)
        val view = firstPageBinding.root
        setContentView(view)


    }
}