package com.pethoalpar.androidtesstwoocr.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kaopiz.kprogresshud.KProgressHUD

import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.model.User
import com.pethoalpar.androidtesstwoocr.model.constructorUser
import com.pethoalpar.androidtesstwoocr.room.UserDao
import com.pethoalpar.androidtesstwoocr.services.Service
import kotlinx.android.synthetic.main.activity_somsri_connect.*
import org.jetbrains.anko.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SomsriConnectActivity : AppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var service: Service
    lateinit var loading: KProgressHUD
    private var intentResult = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_somsri_connect)
        MainApp.graph.inject(this)

        loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f)

        tv_login_somsri.setOnClickListener { _ ->
            loading.show()
            service.getSomsriAccount(et_username_somsri.text.toString(), et_password_somsri.text.toString())
//            service.getSomsriAccount("test@test.com", "1234")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.user != null) {
                            Log.d("PANMAI2", "success!! : " + it.password)
                            val user: User = constructorUser()
                            user.id = it.user.id
                            user.name = it.user.name
                            user.email = it.user.email
//                            user.avatarFileName = it.user.avatarFileName
                            user.status = true
                            userDao.create(user)

                            intentResult.putExtra("userId", it.user.id)
                            intentResult.putExtra("email", it.user.email)
                            intentResult.putExtra("name", it.user.name)
                            intentResult.putExtra("avatarFileName", it.user.avatarFileName)
                            setResult(Activity.RESULT_OK, intentResult)
                            loading.dismiss()
                            finish()
                        } else {
                            toast("username หรือ password ผิด กรุณราตรวจสอบ")
                            loading.dismiss()
                        }

            }, { error ->
                        toast("error")
                Log.d("PANMAI", error.message)
                        finish()
                        loading.dismiss()
            })
        }

    }
}
