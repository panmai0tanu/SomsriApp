package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.util.Log

import java.util.*

@Entity(tableName = "detail_items")
data class DetailItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "detail_item_id") var detailItemId: Int?,

        @ColumnInfo(name = "line_item_id") var lineItemId: Int?,
        @ColumnInfo(name = "detail") var detail: String
)

fun constructorDetailItem(): DetailItem {

    val detail_item = DetailItem(
            0,
            0,
            ""
    )
    return detail_item
}





