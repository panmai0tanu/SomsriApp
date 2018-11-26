package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_detail_items.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.text.TextUtils.isEmpty
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.model.constructorItem
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import java.lang.Math.random
import java.util.*


class DetailItemsActivity : ToolbarActivity() {

    private lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)
        MainApp.graph.inject(this)
        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()

        val totalCost = intent.getStringExtra("totalCost")
        val imgFile = intent.getStringExtra("imgFile")


        et_total_cost.setText(totalCost)

        if (imgFile != null) {
            val bitmap = loadBitmap(imgFile)
            val myBitmap = BitmapFactory.decodeFile(imgFile)
            iv_receipt.setImageBitmap(bitmap)
        }

        iv_receipt.setOnClickListener {
            val intent = Intent(this, ShowImageActivity::class.java)
            intent.putExtra("imgUrl", imgFile)
            startActivity(intent)
        }

        btn_save_order.setOnClickListener {
            val item = constructorItem()
            item.itemId = (0..1000).random()
            while ( itemDao.findByItemId(item.itemId!!).isNotEmpty() )
                item.itemId = (0..1000).random()

            item.totalCost = totalCost.toDouble()
            item.imgUrlFileName = imgFile

            itemDao.create(item)

            finish()
        }

    }

    fun loadBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start
}
