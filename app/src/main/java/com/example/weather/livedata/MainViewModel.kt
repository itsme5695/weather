package com.example.weather.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.models.Weather
import com.example.weather.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var liveData = MutableLiveData<Weather>()
    private val TAG = "MainViewModel"

    fun getWeather(lait:Double,longit:Double): MutableLiveData<Weather> {


        RetrofitClient.apiService.getListWeather(lait,longit,"1fdf5bc5ba7ccbe8ba13c25244b0ef01").enqueue(object:
            Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful){
                    liveData.value = response.body()


                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

        return liveData
    }

}