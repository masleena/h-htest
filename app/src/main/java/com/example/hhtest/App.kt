package com.example.hhtest

import android.app.Application
import com.example.hhtest.dagger.DaggerComponents

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        DaggerComponents.init(this)
    }
}