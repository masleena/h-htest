package com.example.hhtest.dagger

import com.example.hhtest.App
import com.example.hhtest.dagger.app.AppComponent
import com.example.hhtest.dagger.app.DaggerAppComponent

object DaggerComponents {

    lateinit var appComponent: AppComponent

    fun init(app: App) {
        appComponent = DaggerAppComponent.builder().application(app).build()
    }

    val weatherComponent by lazy {
        appComponent.plusWeatherComponent()
    }

    val authComponent by lazy {
        appComponent.plusAuthComponent()
    }
}