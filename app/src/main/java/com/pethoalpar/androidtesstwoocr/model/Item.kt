package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.util.Log

import java.util.*

@Entity(tableName = "items")
data class Item(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "item_id") var itemId: Int?,

        @ColumnInfo(name = "item_type") var itemType: String,
        @ColumnInfo(name = "receipt_number") var receiptNumber: String,
        @ColumnInfo(name = "receipt_form") var receiptForm: String,
        @ColumnInfo(name = "detail") var detail: String = "",
        @ColumnInfo(name = "total_cost") var totalCost: Double,
        @ColumnInfo(name = "effective_date") var effectiveDate: String,
        @ColumnInfo(name = "created_at") var createdAt: String,
        @ColumnInfo(name = "updated_at") var updatedAt: String,
        @ColumnInfo(name = "deleted_at") var deletedAt: String,
        @ColumnInfo(name = "img_url_file_name") var imgUrlFileName: String,
        @ColumnInfo(name = "img_url_content_type") var imgUrlContentType: String,
        @ColumnInfo(name = "img_url_file_size") var imgUrlFileSize: Double,
        @ColumnInfo(name = "img_url_updated_at") var imgUrlUpdatedAt: String
)

fun constructorItem(): Item {

    val date = getCurrentDateTime()

    val item = Item(
            0,
            "",
            "",
            "",
            "",
            0.00,
            date.toString(),
            date.toString(),
            date.toString(),
            date.toString(),
            "",
            "",
            0.00,
            date.toString())
    return item
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}




