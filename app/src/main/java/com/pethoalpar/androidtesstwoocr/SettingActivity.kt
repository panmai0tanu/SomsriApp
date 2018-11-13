package com.pethoalpar.androidtesstwoocr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SettingActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initializeToolbar("Setting")
        useBack()
    }
}
