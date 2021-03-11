package com.example.hhtest.model.repository.user

import com.example.hhtest.dagger.auth.AuthScope
import com.example.hhtest.model.db.dao.UserDao
import com.example.hhtest.model.entity.user.User
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

@AuthScope
class UserRepositoryImpl constructor(private val userDao: UserDao) : IUserRepository {

    val activeUser = BehaviorSubject.create<User>()

    override fun signIn(email: String, password: String): Single<Boolean> {
        return userDao.selectUser(email, password)
            .map {
                activeUser.onNext(it)
                return@map true
            }
    }

    override fun signUp(email: String, password: String): Single<Boolean> {
        return userDao.insert(User(0, email, password))
            .map {
                if (it > 0) {
                    activeUser.onNext(User(it, email, password))
                    return@map true
                } else
                    return@map false
            }
    }
}