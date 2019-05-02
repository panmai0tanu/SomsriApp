package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pethoalpar.androidtesstwoocr.LoginActivity
import com.pethoalpar.androidtesstwoocr.MainActivity
import com.pethoalpar.androidtesstwoocr.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var delayTime: Long = 0
    private var time = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        Glide.with(this)
//                .load(resources.getIdentifier("logo", "drawable", this.packageName))
//                .into(logo_img)

        handler = Handler()

        runnable = Runnable {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        handler!!.postDelayed(runnable, 3000)
        time = System.currentTimeMillis()
    }

    override fun onStop() {
        super.onStop()
        handler!!.removeCallbacks(runnable)
        time = delayTime - (System.currentTimeMillis() - time)
    }
}
