package com.pethoalpar.androidtesstwoocr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class GetTotalPriceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_total_price)

        val result = intent.getStringExtra("result")

        var totalPriceStr = ""
        for (c in result.reversed()) {
            if (c != ' ') totalPriceStr += c
            else break
        }
        totalPriceStr = totalPriceStr.reversed()

        val resultIntent = Intent()
        resultIntent.putExtra("totalPrice", totalPriceStr)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }
}