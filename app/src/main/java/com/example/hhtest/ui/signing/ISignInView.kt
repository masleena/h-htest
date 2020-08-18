package com.example.hhtest.ui.signing

import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.ui.base.IBaseView

interface ISignInView: IBaseView {

    fun showErrorEmailIsIncorrect()

    fun showPasswordHint()

    fun showErrorUserNotFound()

    fun showErrorUserIsExist()

    fun startGeoScanning()

    fun showWeather(weatherData: ReadyWeather)

    fun showProgressAndLockButton()

    fun hideProgressUnlockButton()
}