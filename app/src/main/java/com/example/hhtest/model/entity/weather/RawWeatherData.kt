package com.example.hhtest.model.entity.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val rawWeather: List<RawWeather>,
    @SerializedName("main")    val rawMain: RawMain,
    @SerializedName("wind")    val rawWind: RawWind
)

data class RawWeather(
    val id: Int,
    val main: String,
    val icon: String
)

data class RawMain(
    val temp: Float,
    val humidity: Int
)

data class RawWind(
    val speed: Float,
    val deg: Int
)