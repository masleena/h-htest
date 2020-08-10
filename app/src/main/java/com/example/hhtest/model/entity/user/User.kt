package com.example.hhtest.model.entity.user

import androidx.room.*

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val email: String,
    val password: String)