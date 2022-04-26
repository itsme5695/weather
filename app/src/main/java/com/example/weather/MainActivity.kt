package com.example.weather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.livedata.MainViewModel
import com.example.weather.photoRetrofit.Common
import com.example.weather.photoRetrofit.RetrofitService2

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var retrofitService: RetrofitService2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        retrofitService = Common.retrofitService

        val lait = intent.extras!!.getDouble("lat")
        val longit = intent.extras!!.getDouble("long")

//        Log.d(TAG, "onCreate: lat:${lait},long:$longit")
        mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
        mainViewModel.getWeather(lait, longit).observe(this, Observer {
            changeTemp("${it.main.temp}")
            Log.d(TAG, "onCreate: $it")
            binding.hometown.text = it.name
            binding.wind.text = "Wind: ${it.wind.speed} m/s"
            binding.humitidy.text = "Humidity: ${it.main.humidity}%"
            binding.tiniq.text = "Sky: ${it.weather[0].description}"


        })


    }

    private fun changeTemp(x: String): String? {
        val celsius = x.toDouble() - 273.0
        val i = celsius.toInt()
        binding.gradus.text = "${i}°"
        Log.d(TAG, "changeTemp: $i")
        return i.toString()
    }
}