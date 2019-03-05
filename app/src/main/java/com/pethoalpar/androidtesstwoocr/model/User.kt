package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.util.Log

import java.util.*

@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "user_id") var userId: Int?,

        @ColumnInfo(name = "user_email") var userEmail: String,
        @ColumnInfo(name = "password") var password: String
)

fun constructorUser(): User {

    val user = User(
            0,
            "",
            "")
    return user
}





