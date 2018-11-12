package com.pethoalpar.androidtesstwoocr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_show_data.*

class ShowDataActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)
        initializeToolbar(resources.getString(R.string.somsri_expense))
        useSetting()

        btn_new.setOnClickListener {
            startActivity(Intent(this, SelectReceiptFormActivity::class.java))
        }

        tv_item_1.setOnClickListener {
            if (sv_list_item_detail.visibility == View.GONE){
                sv_list_item_detail.visibility = View.VISIBLE
            } else {
                sv_list_item_detail.visibility = View.GONE
            }
        }

    }
}
