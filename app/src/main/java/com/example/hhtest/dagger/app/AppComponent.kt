package com.example.hhtest.dagger.app

import android.app.Application
import com.example.hhtest.dagger.weather.WeatherComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    fun plusWeatherComponent(): WeatherComponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}