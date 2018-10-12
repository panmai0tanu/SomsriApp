package com.pethoalpar.androidtesstwoocr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_receipt_form.*

class SelectReceiptFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_receipt_form)

        sevenEleven.setOnClickListener { startActivity(Intent(this, TesseractActivity::class.java)) }
    }
}
