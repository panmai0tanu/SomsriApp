package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.toast

class GetTotalPriceActivity : AppCompatActivity() {

    private var languageProcess: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val result = intent.getStringExtra("result")
        languageProcess = intent.getStringExtra("languageProcess")

        toast("Hello")

        if (languageProcess == "eng") {
            var totalPriceStr = ""
            var line = 0
            for (char in result.reversed()) {
                if (char == '\n') line++
                if (line == 3) if (char != ' ') totalPriceStr += char else break

            }
            totalPriceStr = totalPriceStr.reversed()

            val resultIntent = Intent()
            resultIntent.putExtra("totalCost", totalPriceStr)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}