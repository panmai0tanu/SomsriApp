package com.pethoalpar.androidtesstwoocr.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.User

@Dao
interface UserDao {
    @Query("select * from users")
    fun all(): List<User>

    @Query("select * from users where user_id = :userId")
    fun findByUserId(userId: Int): List<User>

    @Insert(onConflict = REPLACE)
    fun create(user: User)

    @Update(onConflict = REPLACE)
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("delete from users")
    fun deleteAll()
}