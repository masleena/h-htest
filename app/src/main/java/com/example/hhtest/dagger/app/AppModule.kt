package com.example.hhtest.dagger.app

import com.example.hhtest.App
import com.example.hhtest.api.API
import com.example.hhtest.api.API.Companion.BASE_URL
import com.example.hhtest.model.db.AppDataBase
import com.example.hhtest.model.repository.user.IUserRepository
import com.example.hhtest.model.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofitClient() = prepareRetrofitClient()

    @Provides
    @Singleton
    fun provideDataBase(app: App) = AppDataBase.getInstance(app.baseContext)

    @Provides
    @Singleton
    fun provideUserRepository(appDataBase: AppDataBase): IUserRepository = UserRepositoryImpl(appDataBase.userDao())

    private fun prepareRetrofitClient() : API {
        val client = prepareOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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