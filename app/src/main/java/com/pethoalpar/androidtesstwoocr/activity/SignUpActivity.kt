package com.pethoalpar.androidtesstwoocr.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.model.constructorUser
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.UserDao
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject


class SignUpActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    private var email: String = ""
    private var pass: String = ""
    private var conPass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tv_signup.setOnClickListener {

            email = et_sign_up_email.text.toString()
            pass = et_sign_up_password.text.toString()
            conPass = et_sign_up_confirm_pass.text.toString()

            if (validatePassword() && isValidEmail(email)) {
                val user = constructorUser()
                user.userId = 0
                user.userEmail = email
                user.password = pass

//                userDao.create(user)

                Log.d("PANMAI", itemDao.all().toString())

            } else {
                Log.d("PANMAI", et_sign_up_email.text.toString() + ", false")
            }
        }

    }

    fun validatePassword(): Boolean {

        return if (!conPass.equals(pass)) {
            tv_error_mess.visibility = View.VISIBLE
            tv_error_mess.setText("Password Not matching")
            false
        } else if (pass.length < 6) {
            tv_error_mess.visibility = View.VISIBLE
            tv_error_mess.setText("Password Not matching")
            false
        } else
            true
    }

    fun isValidEmail(target: String): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}

