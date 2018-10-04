@file:Suppress("DEPRECATION")
package com.pethoalpar.androidtesstwoocr

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
open class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 0
    private lateinit var loadingDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        validatePermission()

        btn_scan.setOnClickListener {
            startActivityForResult(Intent(this, OpencvActivity::class.java), 0)
            loadingDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, false)

        }
    }

    private fun validatePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        finish()
                    }
                }
                ).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val byteArray = data!!.getByteArrayExtra("image")
            val in1 = Intent(this, TesseractActivity::class.java)
            in1.putExtra("image", byteArray)
            startActivityForResult(in1, 1)
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val result = data!!.getStringExtra("result")
            tv_result.text = result
            loadingDialog.dismiss()
        }
    }

}
