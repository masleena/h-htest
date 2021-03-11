package com.example.hhtest.dagger.auth

import com.example.hhtest.model.db.AppDataBase
import com.example.hhtest.model.repository.user.IUserRepository
import com.example.hhtest.model.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideUserRepository(appDataBase: AppDataBase): IUserRepository = UserRepositoryImpl(appDataBase.userDao())

}