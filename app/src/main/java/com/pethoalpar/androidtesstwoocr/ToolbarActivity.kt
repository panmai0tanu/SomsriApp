package com.pethoalpar.androidtesstwoocr

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_toolbar.*
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

open class ToolbarActivity : MainApp() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

    }

    protected fun initializeToolbar() {
        setSupportActionBar(toolbar)
    }

    protected fun initializeToolbar(title: String) {
        setSupportActionBar(toolbar)
        setTitle(title)
    }

    protected fun setTitle(text: String) {
        tv_title.text = text
    }

    protected fun useSetting() {
        btn_setting.visibility = View.VISIBLE
        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    protected fun useBack() {
        btn_back.visibility = View.VISIBLE
        btn_back.setOnClickListener {
            finish()
        }
    }

    protected fun useCamera() {
        btn_camera.visibility = View.VISIBLE
        btn_camera.setOnClickListener {
            finish()
        }
    }
}
