package com.example.weather.photoRetrofit

object Common {
    var BASE_URL = "https://api.unsplash.com/"
    val retrofitService: RetrofitService2
        get() = RetrofitClient2.getRetrofit(BASE_URL).create(RetrofitService2::class.java)

}