package com.example.weather.modelsphoto



data class Photo(
    val results: List<WallpaperResult>,
    val total: Int,
    val total_pages: Int
)