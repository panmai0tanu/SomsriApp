package com.pethoalpar.androidtesstwoocr.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.R.color.white
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.constructorDetailItem
import com.pethoalpar.androidtesstwoocr.model.constructorItem
import com.pethoalpar.androidtesstwoocr.model.constructorLineItem
import com.pethoalpar.androidtesstwoocr.room.DetailItemDao
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.LineItemDao
import kotlinx.android.synthetic.main.activity_detail_items.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject


class DetailItemsActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var lineItemDao: LineItemDao

    @Inject
    lateinit var detailItemDao: DetailItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)
        MainApp.graph.inject(this)
        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()

        val totalCostString = intent.getStringExtra("totalCost")
        val imgFile = intent.getStringExtra("imgFile")
        val checkItem = intent.getStringExtra("item")
        val date = intent.getStringExtra("date")
        val receiptNumber = intent.getStringExtra("receiptNumber")
        val totalCost = if (!totalCostString.isNullOrEmpty()) intOrString(totalCostString) else 0

        btn_save_data.setOnClickListener {

            val item = constructorItem()
            item.itemId = (0..1000).random()
            while (itemDao.findByItemId(item.itemId!!).isNotEmpty())
                item.itemId = (0..1000).random()

            createItem(item)

            val lineItem = constructorLineItem()
            lineItem.itemId = item.itemId
            lineItem.lineItemId = (0..1000).random()
            while (lineItemDao.findByLineItemId(lineItem.lineItemId!!).isNotEmpty())
                lineItem.lineItemId = (0..1000).random()

            //add amount and cost line item

            val detailItem = constructorDetailItem()
            detailItem.lineItemId = lineItem.lineItemId
            detailItem.detailItemId = (0..1000).random()
            while (detailItemDao.findByDetailItemId(detailItem.detailItemId!!).isNotEmpty())
                detailItem.detailItemId = (0..1000).random()

            finish()
        }

        btn_delete_data.setOnClickListener {
            finish()
        }

        Log.d("PANMAI", totalCost.toString() + ',' + receiptNumber + ',' + date )
        iv_angle_down.setOnClickListener {
            if (checkItem != "") {
                et_reciept_id.setText(receiptNumber)
                et_total_cost.setText(totalCost.toString())
                et_date.setText(date)

                if (detail_item.visibility == View.GONE) {
                    detail_item.visibility = View.VISIBLE
                    et_reciept_id.setText(receiptNumber)
                } else {
                    detail_item.visibility = View.GONE
                }
            }
        }


        if (imgFile != null) {
            val bitmap = loadBitmap(imgFile)
            iv_receipt.setImageBitmap(bitmap)
        }

    }

    private fun loadBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }

    private fun intOrString(str: String): Double {
        val v = str.toDoubleOrNull()
        return when (v) {
            null -> 0.00
            else -> v
        }
    }

    private fun createItem(item: Item) {
        item.totalCost = et_total_cost.text.toString().toDouble()
        itemDao.create(item)
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start
}
