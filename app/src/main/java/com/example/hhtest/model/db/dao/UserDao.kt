package com.example.hhtest.model.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hhtest.model.entity.user.User
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM `users` WHERE login=:login AND password=:password")
    fun selectUser(login: String, password: String): Single<User?>
}