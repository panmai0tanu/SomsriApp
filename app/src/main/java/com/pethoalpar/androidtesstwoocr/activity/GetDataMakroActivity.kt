package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
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

        Log.d("PANMAI", '\n' + result +'\n' + "--------------------------- ")

        //Get result string to ArrayList
        var count = 0
        for (char_result in result) {
            if (char_result != '\n') {
                itemStr += char_result
            } else {
                if (itemStr != " " && itemStr != "") {
                    itemList.add(itemStr)
                    Log.d("PANMAI", itemStr)
                    numberLine++
                    itemStr = ""
                }
                count = 0
            }
        }

//        Get data on row Total price
        i = 0
        var itemNum = ""
        var checkOneCha = true
        for (c in itemList[numberLine - 9].reversed()) {
            if (c != ' ' && i == 0) totalPriceStr += c else i++
        }
        totalPriceStr = totalPriceStr.reversed()
        itemNum = itemNum.reversed()

        Log.d("PANMAI", "Total Price: " + totalPriceStr)

        //Get reciept id

        var checkSpace = 0
        var receiptId = ""
        for (c in itemList[2]) {
            if (c == ' ') checkSpace += 1
            else if (checkSpace == 2) receiptId += c
        }

        Log.d("PANMAI", "Receipt ID: " + receiptId)

        checkSpace = 0
        var date = ""
        for (c in itemList[4]) {
            if (c == ' ') checkSpace += 1
            else if (checkSpace == 2) date += c
        }

        Log.d("PANMAI", "Date: " + date)

        intentResult.putExtra("totalCost", totalPriceStr)
        intentResult.putExtra("item", itemText)
        intentResult.putExtra("date", date)
        intentResult.putExtra("receiptNumber", receiptId)
        setResult(Activity.RESULT_OK, intentResult)
        finish()

    }
}
