package com.example.hhtest.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hhtest.model.db.dao.UserDao
import com.example.hhtest.model.entity.user.User
import com.example.hhtest.util.DATABASE_NAME

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private lateinit var INSTANCE: AppDataBase

        fun getInstance(context: Context): AppDataBase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME).build()
                    return INSTANCE
                }
            }
            return INSTANCE
        }
    }
}