package com.example.hhtest.model.entity.weather

data class Weather(
    val typeId: Int,
    val type: String,
    val icon: String,
    val temp: Float,
    val humidity: Int,
    val windSpeed: Float,
    val windDeg: Int
)