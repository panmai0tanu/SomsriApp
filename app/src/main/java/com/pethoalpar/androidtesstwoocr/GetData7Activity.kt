package com.pethoalpar.androidtesstwoocr

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GetData7Activity : AppCompatActivity() {

    private var result = ""
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
        result = intent.getStringExtra("result")

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

        //Get data on row Item
        i = 0
        for (item in itemList) {
            if (itemNum.toInt() > 0 && itemNum.toIntOrNull() != null) {
                if (line > 3 && line < numberLine - 3 && i < itemNum.toInt()){
                    itemText += item
                    itemText += '\n'
                    i++
                }
            } else {
                if (line > 3 && line < numberLine - 3) {
                    itemText += item
                    itemText += '\n'
                }
            }
            line++
        }

        //Get data on row Date and Reciept number
        i = 0
        for (c in itemList[numberLine - 1]) {
            if (c != ' ') {
                if (i == 0) receiptNumber += c
                else if (i == 2) date += c
                else continue
            } else i++
        }

        intentResult.putExtra("totalPrice", totalPriceStr)
        intentResult.putExtra("item", itemText)
        intentResult.putExtra("date", date)
        intentResult.putExtra("receiptNumber", receiptNumber)
        setResult(Activity.RESULT_OK, intentResult)
        finish()

    }

}
