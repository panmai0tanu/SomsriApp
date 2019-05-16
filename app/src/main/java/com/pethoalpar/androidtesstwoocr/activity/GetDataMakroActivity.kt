package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pethoalpar.androidtesstwoocr.R

class GetDataMakroActivity : AppCompatActivity() {

    private var itemStr = ""
    private var numberLine = 0
    private val itemList: ArrayList<String> = ArrayList()
    private var itemText = ""
    private var line = 0
    private var date = ""
    private var receiptNumber = ""
    private var i = 0
    private var totalPriceStr = ""
    private var intentResult = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data_makro)

        val result = intent.getStringExtra("result")

        var showS: String = ""
        for (lineResult in result) {
            if (lineResult != '\n')
                showS += lineResult
            else
                showS += "\n"
        }

        Log.d("PANMAI", result)

        //Get result string to ArrayList
        for (char_result in result) {
            if (char_result != '\n') {
                itemStr += char_result
            } else {
                numberLine++
                itemList.add(itemStr)
                itemStr = ""
            }
        }

        //Get data on row Total price
        i = 0
        var itemNum = ""
        var checkOneCha = true
        for (c in itemList[numberLine - 3].reversed()) {
            if (c != ' ' && i == 0) totalPriceStr += c
            else if (c != ' ' && i == 2 && checkOneCha){
                itemNum += c
                checkOneCha = false
            }
            else i++
        }
        totalPriceStr = totalPriceStr.reversed()
        itemNum = itemNum.reversed()

    }
}
