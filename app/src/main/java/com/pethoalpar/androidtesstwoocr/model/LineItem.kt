package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.util.Log

import java.util.*

@Entity(tableName = "line_items")
data class LineItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "line_item_id") var lineItemId: Int?,

        @ColumnInfo(name = "item_id") var itemId: Int?,
        @ColumnInfo(name = "amount") var amount: Double,
        @ColumnInfo(name = "cost") var cost: Double
)

fun constructorLineItem(): LineItem {

    val line_item = LineItem(
            0,
            0,
            0.00,
            0.00)
    return line_item
}





