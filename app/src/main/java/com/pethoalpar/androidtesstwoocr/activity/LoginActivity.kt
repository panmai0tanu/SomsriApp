package com.pethoalpar.androidtesstwoocr

import android.content.Intent
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.activity.ShowDataActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : MainApp() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login.setOnClickListener {
            startActivity(Intent(this, ShowDataActivity::class.java))
        }
    }

}
