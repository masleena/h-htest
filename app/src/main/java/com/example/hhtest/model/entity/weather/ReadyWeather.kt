package com.example.hhtest.model.entity.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReadyWeather(
    val typeId: Int,
    val type: String,
    val icon: String,
    val temp: Float,
    val humidity: Int,
    val windSpeed: Float,
    val windDeg: Float
) : Parcelable