package com.pethoalpar.androidtesstwoocr.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.pethoalpar.androidtesstwoocr.model.DetailItem
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.LineItem
import com.pethoalpar.androidtesstwoocr.model.User

@Dao
interface DetailItemDao {
    @Query("select * from detail_items")
    fun all(): List<DetailItem>

    @Query("select * from detail_items where line_item_id = :detailItemId")
    fun findByDetailItemId(detailItemId: Int): List<DetailItem>

    @Insert(onConflict = REPLACE)
    fun create(detailItem: DetailItem)

    @Update(onConflict = REPLACE)
    fun update(detailItem: DetailItem)

    @Delete
    fun delete(detailItem: DetailItem)

    @Query("delete from detail_items")
    fun deleteAll()
}