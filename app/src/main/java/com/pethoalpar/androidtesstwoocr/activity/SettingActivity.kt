package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kaopiz.kprogresshud.KProgressHUD
import com.pethoalpar.androidtesstwoocr.LoginActivity
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.model.User
import com.pethoalpar.androidtesstwoocr.model.constructorUser
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.UserDao
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class SettingActivity : ToolbarActivity() {

    @Inject
    lateinit var userDao: UserDao
    private val REQUEST_CODE = 100
    lateinit var loading: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        MainApp.graph.inject(this)

        initializeToolbar("Setting")
        useBack()

        Log.d("PANMAI", userDao.all().toString())

        if (userDao.all().isNotEmpty()){
            setDataUser()
            somsri_connect.setOnClickListener { _ ->
                toast("เชื่อมต่อเรียบร้อยเเล้ว")
            }
        }else {
            somsri_connect.setOnClickListener { _ ->
                startActivityForResult(Intent(this, SomsriConnectActivity::class.java), REQUEST_CODE)
            }
        }

        loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f)

        btn_logout.setOnClickListener {
            loading.show()
            startActivity(Intent(this, ShowDataActivity::class.java))
            btn_logout.visibility = View.GONE
            tv_img_pro.visibility = View.VISIBLE
            tv_img_pro_logined.visibility = View.GONE
            layout_info.visibility = View.GONE
            layout_info_loged.visibility = View.VISIBLE
            userDao.deleteAll()
            finish()
        }

        layout_about.setOnClickListener { _ ->
            startActivity(Intent(this, AbountAppActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val userId = data!!.getStringExtra("userId")
            val email = data.getStringExtra("email")
            val nameUser = data.getStringExtra("name")
            val avatarFileName = data.getStringExtra("avatarFileName")

            userDao.update(userDao.all().first())

            tv_email_loged.text = nameUser
            setDataUser()

            toast("เชื่อมต่อสำเร็จ")

        }

    }

    private fun setDataUser(){
        tv_email_loged.text = userDao.all().first().name
        btn_logout.visibility = View.VISIBLE
        tv_img_pro.visibility = View.GONE
        tv_img_pro_logined.visibility = View.VISIBLE
        layout_info.visibility = View.GONE
        layout_info_loged.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        loading.dismiss()
    }
}
