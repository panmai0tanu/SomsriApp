package com.pethoalpar.androidtesstwoocr.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.LineItem
import com.pethoalpar.androidtesstwoocr.model.User

@Dao
interface LineItemDao {
    @Query("select * from line_items")
    fun all(): List<LineItem>

    @Query("select * from line_items where line_item_id = :lineItemId")
    fun findByLineItemId(lineItemId: Int): List<LineItem>

    @Insert(onConflict = REPLACE)
    fun create(lineItem: LineItem)

    @Update(onConflict = REPLACE)
    fun update(lineItem: LineItem)

    @Delete
    fun delete(lineItem: LineItem)

    @Query("delete from line_items")
    fun deleteAll()
}