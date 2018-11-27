package com.pethoalpar.androidtesstwoocr.dagger

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.room.ItemDao

@Database(entities = [
    Item::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
//    abstract fun itemAllDao(): ItemAllDao
//    abstract fun quickSaleItemDao(): QuickSaleItemDao
}