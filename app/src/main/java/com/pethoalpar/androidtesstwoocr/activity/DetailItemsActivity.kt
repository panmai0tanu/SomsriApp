package com.pethoalpar.androidtesstwoocr.activity

import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity

class DetailItemsActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)

        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()
    }
}
