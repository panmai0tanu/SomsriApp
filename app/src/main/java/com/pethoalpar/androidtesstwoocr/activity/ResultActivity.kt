@file:Suppress("DEPRECATION")

package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_main.*


open class ResultActivity : ToolbarActivity() {

    private lateinit var loadingDialog: ProgressDialog
    private val REQUEST_CODE = 1
    private var imgFile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val result = intent.getStringExtra("result")
        val languageProcess = intent.getStringExtra("languageProcess")
        imgFile = intent.getStringExtra("imgFile")

        val intent = (Intent(this, GetTotalPriceActivity::class.java))
        intent.putExtra("result", result)
        intent.putExtra("languageProcess", languageProcess)
        startActivityForResult(intent, REQUEST_CODE)

        runOnUiThread { loadingDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, false) }

        btn_scan.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val totalCost = data!!.getStringExtra("totalCost")

            val intent = Intent(this, DetailItemsActivity::class.java)
            intent.putExtra("totalCost", totalCost)
            intent.putExtra("imgFile", imgFile)
            startActivity(intent)
            finish()
        }

        loadingDialog.dismiss()

    }

}
