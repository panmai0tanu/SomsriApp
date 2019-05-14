package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.LoginActivity
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast

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

        layout_about.setOnClickListener { _ ->
            startActivity(Intent(this, AbountAppActivity::class.java))
        }

        somsri_connect.setOnClickListener { _ ->
            startActivity(Intent(this, SomsriConnectActivity::class.java))
            finish()
        }

    }
}
