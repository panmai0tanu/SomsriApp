package com.pethoalpar.androidtesstwoocr.activity

import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity

class SettingActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initializeToolbar("Setting")
        useBack()
    }
}
