package com.pethoalpar.androidtesstwoocr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pethoalpar.androidtesstwoocr.activity.ShowDataActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login.setOnClickListener {
            startActivity(Intent(this, ShowDataActivity::class.java))
        }
    }

}
