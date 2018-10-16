@file:Suppress("DEPRECATION")
package com.pethoalpar.androidtesstwoocr

import android.Manifest
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

open class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_TESS = 0
    private val REQUEST_CODE_GET7 = 1
    private lateinit var loadingDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        validatePermission()
//        startActivity(Intent(this, SelectReceiptFormActivity::class.java))
    }

    private fun validatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                        }

                        override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {/* ... */
                        }
                    }).check()
        }
    }

}
