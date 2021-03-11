package com.example.hhtest.dagger.app

import com.example.hhtest.App
import com.example.hhtest.dagger.auth.AuthComponent
import com.example.hhtest.dagger.weather.WeatherComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun plusAuthComponent(): AuthComponent
    fun plusWeatherComponent(): WeatherComponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }
}