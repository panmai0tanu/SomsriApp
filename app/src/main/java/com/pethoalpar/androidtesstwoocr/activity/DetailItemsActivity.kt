package com.pethoalpar.androidtesstwoocr.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.constructorItem
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_detail_items.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject


class DetailItemsActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)
        MainApp.graph.inject(this)
        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()

        val totalCostString = intent.getStringExtra("totalCost")
        val imgFile = intent.getStringExtra("imgFile")
        val totalCost = intOrString(totalCostString)

        et_total_cost.setText(totalCost.toString())

        btn_save_data.setOnClickListener {

            val item = constructorItem()
            item.itemId = (0..1000).random()
            while (itemDao.findByItemId(item.itemId!!).isNotEmpty())
                item.itemId = (0..1000).random()

            createItem(item)

            finish()
        }

        btn_delete_data.setOnClickListener {
            finish()
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
