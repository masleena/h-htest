package com.example.hhtest.model.repository.user

import io.reactivex.Observable

interface IUserRepository {

    fun auth(login: String, password: String): Observable<Boolean>

    fun reg(login: String, password: String): Observable<Boolean>
}