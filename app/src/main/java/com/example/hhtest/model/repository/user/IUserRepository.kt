package com.example.hhtest.model.repository.user

import io.reactivex.Observable

interface IUserRepository {

    fun signIn(email: String, password: String): Observable<Boolean>

    fun signUp(email: String, password: String): Observable<Boolean>
}