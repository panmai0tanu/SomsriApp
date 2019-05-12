package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.LoginActivity
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initializeToolbar("Setting")
        useBack()

        btn_logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        layout_about.setOnClickListener {
            startActivity(Intent(this,AbountAppActivity::class.java))
        }

    }
}
