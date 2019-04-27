package com.pethoalpar.androidtesstwoocr.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.model.LineItem
import com.pethoalpar.androidtesstwoocr.room.DetailItemDao
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.LineItemDao
import com.pethoalpar.androidtesstwoocr.room.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mainApp: MainApp) {

    @Provides
    @Singleton
    fun provideMainApp() = mainApp

    @Provides
    @Singleton
    fun provideContext(): Context = mainApp.applicationContext

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "somsri_app")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

    @Provides
    @Singleton
    fun providesItemDao(database: AppDatabase): ItemDao = database.itemDao()

    @Provides
    @Singleton
    fun providesUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providesLineItemDao(database: AppDatabase): LineItemDao = database.lineItemDao()

    @Provides
    @Singleton
    fun providesDetailItemDao(database: AppDatabase): DetailItemDao = database.detailItemDao()

}