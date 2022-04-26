package com.example.weather.retrofit

import com.example.weather.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("weather")
    fun getListWeather(
    @Query("lat") latitude: Double,
    @Query("lon") longitude: Double,
    @Query("appid") API_KEY: String
    ): Call<Weather>


}