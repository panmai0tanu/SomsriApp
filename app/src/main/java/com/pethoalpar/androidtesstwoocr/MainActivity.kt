@file:Suppress("DEPRECATION")
package com.pethoalpar.androidtesstwoocr

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*



open class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_TESS = 0
    private val REQUEST_CODE_GET7 = 1
    private lateinit var loadingDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        validatePermission()

        btn_scan.setOnClickListener {
            startActivityForResult(Intent(this, TesseractActivity::class.java), REQUEST_CODE_TESS)
            runOnUiThread { loadingDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, false) }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_TESS && resultCode == Activity.RESULT_OK) {
            val result = data!!.getStringExtra("result")
            val intent = (Intent(this, GetData7Activity::class.java))
            intent.putExtra("result", result)
            startActivityForResult(intent, 1)
        } else if (requestCode == REQUEST_CODE_GET7 && resultCode == Activity.RESULT_OK) {
            tv_date.text = data!!.getStringExtra("date")
            tv_receipt_num.text = data.getStringExtra("receiptNumber")
            tv_total_price.text = data.getStringExtra("totalPrice")
            tv_items.text = data.getStringExtra("item")
        }

        loadingDialog.dismiss()

    }

}
