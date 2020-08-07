package com.example.hhtest.model.entity.user

import androidx.room.*

@Entity(tableName = "users", indices = [Index(value = ["login"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val login: String,
    val password: String)