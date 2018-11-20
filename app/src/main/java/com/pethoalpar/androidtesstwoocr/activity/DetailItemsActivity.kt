package com.pethoalpar.androidtesstwoocr.activity

import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_detail_items.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap


class DetailItemsActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)

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

    }

    fun loadBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }
}
