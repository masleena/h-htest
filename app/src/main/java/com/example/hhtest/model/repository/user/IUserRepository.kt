package com.example.hhtest.model.repository.user

import io.reactivex.Single

interface IUserRepository {

    fun signIn(email: String, password: String): Single<Boolean>

    fun signUp(email: String, password: String): Single<Boolean>
}