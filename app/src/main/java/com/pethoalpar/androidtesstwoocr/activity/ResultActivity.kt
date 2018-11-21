@file:Suppress("DEPRECATION")
package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.constructorItem
//import com.pethoalpar.androidtesstwoocr.model.constructorItem
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


open class ResultActivity : ToolbarActivity() {

    private lateinit var loadingDialog: ProgressDialog
    private val REQUEST_CODE = 1
    lateinit var itemDao: ItemDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        MainApp.graph.inject(this)

        val result = intent.getStringExtra("result")
        val intent = (Intent(this, GetTotalPriceActivity::class.java))
        intent.putExtra("result", result)
        startActivityForResult(intent, 1)

        runOnUiThread { loadingDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, false) }

        btn_scan.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            tv_total_price.text = data!!.getStringExtra("totalPrice")

            val totalCost = data.getStringExtra("totalPrice").toInt()
            val item = constructorItem()
            item.totalCost = totalCost.toDouble()
            createItem(item)
        }

        loadingDialog.dismiss()

    }

    private fun createItem(item: Item) {
        itemDao.create(item)
    }



}
