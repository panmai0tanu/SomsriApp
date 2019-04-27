package com.pethoalpar.androidtesstwoocr.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.util.Log

import java.util.*

@Entity(tableName = "items")
data class Item(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "item_id") var itemId: Int,

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

    return Item(
            0,
            "",
            "",
            "",
            "",
            0.00,
            date,
            date,
            date,
            date,
            "",
            "",
            0.00,
            date)
}

fun getCurrentDateTime(): String {
    val date = Calendar.getInstance().time
    val listStringDate = date.toString().split(" ")
    return listStringDate[2] + "/" + getIntDate(listStringDate[1]) + "/" + listStringDate[5]
}


fun getIntDate(date: String): String {
    val intDate: String
    if (date == "Jan")
        intDate = "01"
    else if (date == "Feb")
        intDate = "02"
    else if (date == "Mar")
        intDate = "03"
    else if (date == "Apr")
        intDate = "04"
    else if (date == "May")
        intDate = "05"
    else if (date == "Jun")
        intDate = "06"
    else if (date == "July")
        intDate = "07"
    else if (date == "Aug")
        intDate = "08"
    else if (date == "Sep")
        intDate = "09"
    else if (date == "Oct")
        intDate = "10"
    else if (date == "Nov")
        intDate = "11"
    else if (date == "Dec")
        intDate = "12"
    else
        intDate = "00"

    return intDate
}

fun getMonthName(monthNum: String): String {
    when (monthNum) {
        "01" -> return ("มกราคม")
        "02" -> return ("กุมภาพันธ์")
        "03" -> return ("มีนาคม")
        "04" -> return ("เมษายน")
        "05" -> return ("พฤษภาคม")
        "06" -> return ("มิถุนายน")
        "07" -> return ("กรกฏาคม")
        "08" -> return ("สิงหาคม")
        "09" -> return ("กันยายน")
        "10" -> return ("ตุลาคม")
        "11" -> return ("พฤศจิกายน")
        "12" -> return ("ธันวาคม")
        else -> return ""
    }
}





