package com.example.hhtest.dagger.app

import android.app.Application
import com.example.hhtest.api.API
import com.example.hhtest.api.API.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApp(application: Application) = application

    @Provides
    @Singleton
    fun provideRetrofitClient() = prepareRetrofitClient()

    private fun prepareRetrofitClient() : API {
        val client = prepareOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    private fun prepareOkHttpClient() = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()
}