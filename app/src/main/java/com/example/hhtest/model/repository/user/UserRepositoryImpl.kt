package com.example.hhtest.model.repository.user

import com.example.hhtest.model.db.dao.UserDao
import com.example.hhtest.model.entity.user.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : IUserRepository {

    val activeUser = BehaviorSubject.create<User>()

    override fun auth(login: String, password: String): Observable<Boolean> {
        return userDao.selectUser(login, password)
            .toObservable()
            .map {
                activeUser.onNext(it)
                return@map true
            }
    }

    override fun reg(login: String, password: String): Observable<Boolean> {
        return userDao.insert(User(0, login, password))
            .toObservable()
            .map {
                if (it > 0) {
                    activeUser.onNext(User(it, login, password))
                    return@map true
                } else
                    return@map false
            }
    }
}