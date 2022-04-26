package com.example.weather.photoRetrofit

import com.example.weather.modelsphoto.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService2 {

    @GET("search/photos")
    fun getListWallpaper
                (@Query("query") query: String,
                 @Query("client_id") key:String
    ): Call<Photo>
}