package com.pethoalpar.androidtesstwoocr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity

class AbountAppActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abount_app)
        setTitle("เกี่ยวกับ Somsri Expense")
        useBack()
    }
}
