package com.pethoalpar.androidtesstwoocr.dagger

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.User
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.UserDao

@Database(entities = [
    Item::class,
    User::class
], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun userDao(): UserDao
//    abstract fun itemAllDao(): ItemAllDao
//    abstract fun quickSaleItemDao(): QuickSaleItemDao
}