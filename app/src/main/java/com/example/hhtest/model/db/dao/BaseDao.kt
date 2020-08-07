package com.example.hhtest.model.db.dao

import androidx.room.*
import io.reactivex.Single

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: T): Single<Long>

    @Delete
    fun delete(entity: T): Single<Int>

    @Update
    fun update(entity: T): Single<Int>
}