package com.example.hhtest.ui.signing

import com.example.hhtest.model.entity.weather.Weather
import com.example.hhtest.ui.base.IBaseView

interface ISignInView: IBaseView {

    fun showErrorEmailIsIncorrect()

    fun showPasswordHint()

    fun showErrorUserNotFound()

    fun showErrorUserIsExist()

    fun showWeather(weatherData: Weather?)
}