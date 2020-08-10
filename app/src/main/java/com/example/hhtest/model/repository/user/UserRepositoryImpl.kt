package com.example.hhtest.model.repository.user

import com.example.hhtest.model.db.dao.UserDao
import com.example.hhtest.model.entity.user.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class UserRepositoryImpl constructor(private val userDao: UserDao) : IUserRepository {

    val activeUser = BehaviorSubject.create<User>()

    override fun signIn(email: String, password: String): Observable<Boolean> {
        return userDao.selectUser(email, password)
            .toObservable()
            .map {
                activeUser.onNext(it)
                return@map true
            }
    }

    override fun signUp(email: String, password: String): Observable<Boolean> {
        return userDao.insert(User(0, email, password))
            .toObservable()
            .map {
                if (it > 0) {
                    activeUser.onNext(User(it, email, password))
                    return@map true
                } else
                    return@map false
            }
    }
}