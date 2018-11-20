package com.pethoalpar.androidtesstwoocr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DetailItemsActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)

        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()
    }
}
