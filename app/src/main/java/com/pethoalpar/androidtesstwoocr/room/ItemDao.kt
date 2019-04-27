package com.pethoalpar.androidtesstwoocr.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.pethoalpar.androidtesstwoocr.model.Item

@Dao
interface ItemDao {
    @Query("select * from items")
    fun all(): List<Item>

    @Query("select * from items where item_id = :itemId")
    fun findByItemId(itemId: Int): List<Item>

    @Query("select * from items where item_id = :itemId")
    fun findItem(itemId: Int): Item

    @Insert(onConflict = REPLACE)
    fun create(item: Item)

    @Update(onConflict = REPLACE)
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("delete from items")
    fun deleteAll()
}