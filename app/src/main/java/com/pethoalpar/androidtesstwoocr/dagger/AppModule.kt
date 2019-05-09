package com.pethoalpar.androidtesstwoocr.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.pethoalpar.androidtesstwoocr.BuildConfig
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.model.LineItem
import com.pethoalpar.androidtesstwoocr.room.DetailItemDao
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.LineItemDao
import com.pethoalpar.androidtesstwoocr.room.UserDao
import com.pethoalpar.androidtesstwoocr.services.APIService
import com.pethoalpar.androidtesstwoocr.services.Service
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
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
    fun provideApiService(): APIService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl("https://somsriapp.herokuapp.com/user_api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideService() = Service()

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