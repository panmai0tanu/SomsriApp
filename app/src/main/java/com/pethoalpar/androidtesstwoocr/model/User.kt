package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.util.Log

import java.util.*

@Entity(tableName = "users")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id") var id: Int,

        @ColumnInfo(name = "email") var email: String,
        @ColumnInfo(name = "created_at") var createdAt: String,
        @ColumnInfo(name = "updated_at") var updatedAt: String,
        @ColumnInfo(name = "school_id") var schoolId: Int,
        @ColumnInfo(name = "full_name") var fullName: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "avatar_file_name") var avatarFileName: String,
        @ColumnInfo(name = "avatar_content_type") var avatarContentType: String,
        @ColumnInfo(name = "avatar_file_size") var avatarFileSize: Double,
        @ColumnInfo(name = "avatar_updated_at") var avatarUpdateAt: String,
        @ColumnInfo(name = "password") var password: String,
        @ColumnInfo(name = "status") var status: Boolean

)

fun constructorUser(): User {

    val date = getCurrentDateTime()
    val user = User(
            0,
            "",
            date,
            date,
            0,
            "",
            "",
            "",
            "",
            0.00,
            "",
            "",
            false)
    return user
}





