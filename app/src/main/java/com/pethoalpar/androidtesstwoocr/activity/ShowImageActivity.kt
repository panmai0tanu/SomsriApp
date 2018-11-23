package com.pethoalpar.androidtesstwoocr.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_detail_items.*
import kotlinx.android.synthetic.main.activity_show_image.*

class ShowImageActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        initializeToolbar()
        useEdit()
        useBack()

        val imgFile = intent.getStringExtra("imgUrl")

        if (imgFile != null) {
            val bitmap = loadBitmap(imgFile)
            val myBitmap = BitmapFactory.decodeFile(imgFile)
            iv_show_img.setImageBitmap(bitmap)
        }
    }

    fun loadBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }
}
