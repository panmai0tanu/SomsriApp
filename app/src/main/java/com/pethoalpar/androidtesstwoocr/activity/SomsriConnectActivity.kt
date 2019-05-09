package com.pethoalpar.androidtesstwoocr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.services.Service
import kotlinx.android.synthetic.main.activity_somsri_connect.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SomsriConnectActivity : AppCompatActivity() {

    @Inject
    lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_somsri_connect)
        MainApp.graph.inject(this)


        tv_login_somsri.setOnClickListener { _ ->
            service.getSomsriAccount()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                Log.d("PANMAI", it.user.toString())
            }, { error ->
                Log.d("PANMAI", error.message)
            })
        }

    }
}
