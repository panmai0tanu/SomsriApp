package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pethoalpar.androidtesstwoocr.R

class TescoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tesco)

        val result = intent.getStringExtra("result")

        Log.d("PANMAI", '\n' + result +'\n' + "*********************")

        //Get result string to ArrayList
        var itemStr = ""
        val itemList:ArrayList<String> = ArrayList()
        var numberLine = 0
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
            }
        }
        itemList.add(itemStr)
        numberLine++

        //Get reciept id
        var receiptId = ""
        var checkSpace = 0
        for (c in itemList[1]){
            if (c == ' ') checkSpace++
            else if (checkSpace == 2) receiptId += c
        }
        Log.d("PANMAI", "Receipt ID: " + receiptId)

        //Get totalPrice
        var totalPrice = ""
        for (c in itemList[numberLine - 6].reversed()) {
            if (c == ' ' ) break
            else totalPrice += c
        }
        totalPrice = totalPrice.reversed()
        Log.d("PANMAI", "Total Price: " + totalPrice)

        //Get date
        var date = ""
        for (c in itemList[numberLine - 1]) {
            if (c == ' ' ) break
            else date += c
        }

        val intentResult = Intent()
        intentResult.putExtra("totalCost", totalPrice)
        intentResult.putExtra("date", date)
        intentResult.putExtra("receiptNumber", receiptId)
        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }
}
