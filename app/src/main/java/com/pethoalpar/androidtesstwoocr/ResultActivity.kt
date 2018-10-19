@file:Suppress("DEPRECATION")
package com.pethoalpar.androidtesstwoocr

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



open class ResultActivity : AppCompatActivity() {

    private lateinit var loadingDialog: ProgressDialog
    private val REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

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
//            tv_date.text = data!!.getStringExtra("date")
//            tv_receipt_num.text = data.getStringExtra("receiptNumber")
            tv_total_price.text = data!!.getStringExtra("totalPrice")
//            tv_items.text = data.getStringExtra("item")
        }

        loadingDialog.dismiss()

    }

}
